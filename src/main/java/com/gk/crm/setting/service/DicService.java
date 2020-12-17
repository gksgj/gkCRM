package com.gk.crm.setting.service;

import com.gk.crm.setting.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll();
}
