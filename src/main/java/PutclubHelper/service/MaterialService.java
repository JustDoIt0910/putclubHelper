package PutclubHelper.service;

import PutclubHelper.pojo.Materials;

public interface MaterialService {
    public void save(String id, String text);
    public Materials get(String id);
}
