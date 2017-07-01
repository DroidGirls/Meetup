package com.hkurokawa.rxjavaexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.hkurokawa.rxjavaexample.databinding.ContributorsListItemFullnameBinding;
import com.hkurokawa.rxjavaexample.network.GitHubService;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity6 extends AppCompatActivity {
  private static final String TAG = MainActivity6.class.getSimpleName();
  private static final String CLIENT_ID = "XXXXX";
  private static final String CLIENT_SECRET = "XXXXX";
  private EditText ownerEditText;
  private EditText repositoryEditText;
  private ContributorsAdapter adapter;
  private GitHubService service;
  private SerialDisposable disposable = new SerialDisposable();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ownerEditText = (EditText) findViewById(R.id.owner);
    repositoryEditText = (EditText) findViewById(R.id.repository);
    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new ContributorsAdapter(this);
    recyclerView.setAdapter(adapter);

    final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    final OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
        .addNetworkInterceptor(
            new GitHubOAuthAppAuthenticationInterceptor(CLIENT_ID, CLIENT_SECRET))
        .build();

    service = new Retrofit.Builder().baseUrl("https://api.github.com")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GitHubService.class);

    final Button loadButton = (Button) findViewById(R.id.load);
    loadButton.setOnClickListener(view -> onLoadClicked());

    Observable.combineLatest(RxTextView.textChanges(ownerEditText).map(s -> s.length() > 0),
        RxTextView.textChanges(repositoryEditText).map(s -> s.length() > 0),
        (ownerNotEmpty, repositoryNotEmpty) -> ownerNotEmpty && repositoryNotEmpty)
        .subscribe(loadButton::setEnabled);
  }

  private void onLoadClicked() {
    final String owner = ownerEditText.getText().toString();
    final String repository = repositoryEditText.getText().toString();
    disposable.set(service.contributors(owner, repository)
        .subscribeOn(Schedulers.io())
        .flatMap(Observable::fromIterable)
        .take(5)
        .concatMapEager(contributor -> service.user(contributor.getLogin()).subscribeOn(Schedulers.io()))
        .map(user -> new ContributorsListItem(user.getLogin(), user.getName()))
        .toList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            contributors -> adapter.setContributors(contributors),
            throwable -> Log.e(TAG,
                "Failed to fetch the contributors list: " + throwable.getMessage())));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    disposable.dispose();
  }

  private static class ContributorsAdapter
      extends RecyclerView.Adapter<ContributorsListItemViewHolder> {
    private final List<ContributorsListItem> contributors;
    private final LayoutInflater inflater;

    private ContributorsAdapter(Context context) {
      inflater = LayoutInflater.from(context);
      contributors = new ArrayList<>();
    }

    @Override
    public ContributorsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ContributorsListItemViewHolder(
          ContributorsListItemFullnameBinding.inflate(inflater, parent, false));
    }

    @Override public void onBindViewHolder(ContributorsListItemViewHolder holder, int position) {
      holder.bind(contributors.get(position));
    }

    @Override public int getItemCount() {
      return contributors.size();
    }

    public void setContributors(List<ContributorsListItem> contributors) {
      Log.d(TAG, "Updating contributors.");
      this.contributors.clear();
      this.contributors.addAll(contributors);
      notifyDataSetChanged();
    }
  }

  private static class ContributorsListItemViewHolder extends RecyclerView.ViewHolder {
    private final ContributorsListItemFullnameBinding binding;

    public ContributorsListItemViewHolder(ContributorsListItemFullnameBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(ContributorsListItem contributor) {
      binding.setContributor(contributor);
      binding.executePendingBindings();
    }
  }
}
