package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.entity.CashClosure;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;

public interface ICashClosureService {

    CashClosure generateClosure(UserPerson user);
}
