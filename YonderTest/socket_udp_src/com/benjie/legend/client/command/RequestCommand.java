/*
] * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjie.legend.client.command;


/**
 *
 * @author jackyli515
 */
public interface RequestCommand {

    public static final short SynClock = 20001;// 同步时间
    public static final short GetGameProfile = 20002;// 获取游戏档案
    public static final short GetGameProfileListCommand = 20004;// 获取游戏档案
    public static final short CreateGameProfile = 20003;// 创建玩家档案

    // 主城
    public static final short CityMoveCommand = 21201;// 主城移动
    public static final short SendEmojiCommand = 21202;// 主城移动、发表情
    public static final short EnterCityCommand = 21203;// 进入主城
    public static final short BackCityCommand = 21204;// 返回主城
}
