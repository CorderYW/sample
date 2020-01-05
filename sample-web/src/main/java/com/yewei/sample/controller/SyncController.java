package com.yewei.sample.controller;

import com.yewei.sample.sync.LockTaskBiz;
import com.yewei.sample.sync.SyncLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncLockHelper syncLockHelper;

    @GetMapping("/testSyncLock")
    public boolean sendSms(Long userId) throws Exception{
       return syncLockHelper.lock(userId.toString(),()->test());
    }

    public boolean test(){
        return true;
    }
}
