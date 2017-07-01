package com.hkurokawa.rxjavaexample;

import com.hkurokawa.rxjavaexample.network.Contributor;
import java.util.ArrayList;
import java.util.List;

public class ContributorsListItem {
  public final String login;
  public final String name;

  public ContributorsListItem(String login, String name) {
    this.login = login;
    this.name = name;
  }

  public ContributorsListItem(String login) {
    this(login, null);
  }

  public static List<ContributorsListItem> fromJson(List<Contributor> list) {
    final List<ContributorsListItem> ret = new ArrayList<>();
    //noinspection Convert2streamapi
    for (Contributor c : list) {
      ret.add(new ContributorsListItem(c.getLogin()));
    }
    return ret;
  }
}
