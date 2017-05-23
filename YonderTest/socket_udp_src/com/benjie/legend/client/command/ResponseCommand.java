package com.benjie.legend.client.command;

public interface ResponseCommand {
    /**
     * 
     * @param param
     * @return 返回要输出的内容
     */
    public void handle(String data);
    
	//基础模块
	public static final int GetGameProfile = 50001;

	//主城同步
	public static final int PlayerMove = 50701;//玩家移动
	public static final int LeaveSpace = 50702;//玩家离开场景
	public static final int SyncPlayerPosInfo = 50703;//主动同步玩家信息
	public static final int BeSyncPlayerPosInfo = 50704;//被动同步玩家信息
	public static final int SendEmoji = 50705;//发表情
    public static final int EnterSpace = 50706;//进入新场景
    public static final int ChangeRide = 50707;//更换坐骑
    public static final int ChangeTitle = 50708;//更换称号
    public static final int ChangeDress = 50709;//更新时装
    public static final int ChangeName = 50710;//更新名字
    public static final int ChangePet = 50711;//更新宠物

}
