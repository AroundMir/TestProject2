package com.miros.testproject.controller.activity;


import com.miros.testproject.controller.BaseClassController;
import com.miros.testproject.data.entity.Device;
import com.miros.testproject.data.entity.User;
import com.miros.testproject.data.entity.UserActivity;
import com.miros.testproject.exception.RuntimeEx;
import com.miros.testproject.service.DeviceService;
import com.miros.testproject.service.UserActivityService;
import com.miros.testproject.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserActivityController extends BaseClassController {
    private UserService userService = new UserService();
    private DeviceService deviceService = new DeviceService();
    private UserActivityService userActivityService = new UserActivityService();
    private final static Logger log = LoggerFactory.getLogger(UserActivityController.class);

    //Stream?
    /*
       Map Device id, device count;
      */
    public void create(int userId, Map<Integer, Integer> deviceMap, LocalDate localDate) {
        try {
            boolean userCheck = userService.exist(userId);
            List<Device> deviceList = new ArrayList();
            User user;
            if (userCheck) {
                user = userService.find(userId);
                for (Map.Entry<Integer, Integer> entry : deviceMap.entrySet()) {
                    int deviceId = entry.getKey();
                    if (!deviceService.exist(deviceId)) {
                        utils.printLine("Device with " + deviceId + " number doesn't exist");
                        throw new RuntimeEx("UserActivity create: Device doesnt exist id: " + deviceId);
                    } else {
                        Device device = deviceService.find(deviceId);
                        deviceList.add(device);
                        log.info("UserActivity create: Device added in UserActivity.deviceList, Device id: " + deviceId);
                    }
                }
                UserActivity userActivity = new UserActivity(user, deviceList, localDate);
                userActivityService.save(userActivity);
                log.info("UserActivity create: UserActivity created id: " + userActivity.getId());
                utils.printLine("Purchase created");
                waitForEnter();

            }
        } catch (RuntimeEx e) {
            log.info(e.getMessage(), e);
        }
        utils.printLine("User with id: " + userId + " doesn't exist");
        waitForEnter();
    }

    public void delete(Integer id) {
        try {
            userActivityService.delete(id);
            utils.printLine("Purchase deleted");
            waitForEnter();
        } catch (RuntimeEx e) {
            utils.printLine("Purchase with id" + id + " doesn't exist");
            log.info("UserActivity create: Delete UserActivity Runtime problem, id: " + id, e);
            waitForEnter();
        }
    }
}
