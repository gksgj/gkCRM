package com.gk.crm.workbench.service;

import com.gk.crm.workbench.domain.Clue;
import com.gk.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aid);


    boolean convert(String clueId, Tran t, String createBy);
}
