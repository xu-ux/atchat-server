package com.xu.atchat.netty.strategy;

import com.xu.atchat.constant.action.ActionTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/7/7 23:20
 * @description
 */
@Slf4j
public class FlyweightFactory {

    private static class Sinletonleader{
        private static HeartActionStrategy heartActionStrategy = new HeartActionStrategy();
        private static ConnectActionStrategy connectActionStrategy = new ConnectActionStrategy();
        private static SignActionStrategy signActionStrategy = new SignActionStrategy();
        private static TextActionStrategy textActionStrategy = new TextActionStrategy();
        private static ImgActionStrategy imgActionStrategy = new ImgActionStrategy();
        private static VoiceActionStrategy voiceActionStrategy = new VoiceActionStrategy();
    }

    private static Map<ActionTypeEnum,ActionStrategy> flyweightMap = new ConcurrentHashMap<>();

    public static ActionStrategy getFlyweight(ActionTypeEnum actionTypeEnum) {
        if (flyweightMap.containsKey(actionTypeEnum)) {
            return flyweightMap.get(actionTypeEnum);
        } else {
            ActionStrategy actionStrategy;
            switch (actionTypeEnum){
                case heart:
                    actionStrategy = Sinletonleader.heartActionStrategy;
                    break;
                case connect:
                    actionStrategy = Sinletonleader.connectActionStrategy;
                    break;
                case sign:
                    actionStrategy = Sinletonleader.signActionStrategy;
                    break;
                case text:
                    actionStrategy = Sinletonleader.textActionStrategy;
                    break;
                case img:
                    actionStrategy = Sinletonleader.imgActionStrategy;
                    break;
                case voice:
                    actionStrategy = Sinletonleader.voiceActionStrategy;
                    break;
                default:
                    log.error("未找到其他的策略,action:{}",actionTypeEnum.name());
                    actionStrategy = null;
                    break;
            }
            flyweightMap.put(actionTypeEnum, actionStrategy);
            return actionStrategy;
        }
    }

}
