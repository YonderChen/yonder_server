/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.parameter;

import com.yonder.game.server.command.ICommand;


public class Constants{
	public static final int Success = 0;//成功的状态

    //编码
    public static final String Charset = "UTF-8";
    /**
     * 允许同一玩家多台设备同时访问的接口
     */
    public static final short[] IgoreCheckMacArray = new short[]{
    	ICommand.SynClock,
    	ICommand.SynGameConfigs,
    	ICommand.GetGameProfile,
    	ICommand.CreatePlayerInfo,
    	ICommand.GetBattleAgentInfo,
    	ICommand.GetInitBattleAgentInfo,
    	ICommand.GetSkillEventInfo
    };

    /**
     * 允许不校验数字签名的接口
     */
    public static final short[] IgoreCheckSignArray = new short[]{
    	ICommand.GetBattleAgentInfo,
    	ICommand.GetInitBattleAgentInfo,
    	ICommand.GetSkillEventInfo
    };
    
    public static final String SepCharOfNormal = ",";//普通使用的分隔符
    
    public static final String SystemGpId = "10000";//系统编号
    
    public static final String SystemName = "game";//系统编号
    
    public static final String BattleProp = "battleProp";
    
    public static final int RemoveFromPlayerMapPeriod = 1200; //将20分钟没有活跃过的用户从内存中剔除 单位：秒

    public static final int OnlineTimeInUnixtime = 300;//秒，超过300秒未做操作的玩家视为离线
    public static final int OnlineTime15MinInUnixtime = 900;//秒，统计900秒在线玩家
    public static final int OnlineTime30MinInUnixtime = 1800;//秒，统计1800秒在线玩家
    
    public static final int AllRankOfArena = -1;//竞技场所有排名
    public static final int NonRankOfArena = 9999999;//竞技场默认初始排名
    public static final int MaxPcRankOfArena = 9999;//竞技场初始pc排名数
    public static final String ArenaPcGpIdPre = "arena_pc_rank_";//竞技场初始排名
    public static final String ArenaPcName = "arena_pc";//竞技场初始排名
    
    public static final String Rate = "rate";//随机json中的概率key
    
    public static final String RewardTypeKey = "rewardType";//奖励类型key
    
//    public final class Nature {
//    	public static final int None = 0;	//无属性
//    	public static final int Water = 1;	//水
//    	public static final int Fire = 2;	//火
//    	public static final int Wood = 3;	//木
//    	public static final int Light = 4;	//光
//    	public static final int Dark = 5;	//暗
//    }

	
	public final class EquipmentType {
		public static final int Weapon = 0;	//武器
		public static final int Helmet = 1;	//头盔
		public static final int Ornament = 2;//饰品
		public static final int Armor = 3;	//护甲
		public static final int Glove = 4;	//手套
		public static final int Shoe = 5;	//战靴
	}

	
	public static final int SuitNone = 0;
	
	public static final int SuitEquipmentNum = 6;
	
    public enum Nature{
    	NONE(0),	//无属性
    	WATER(1),	//水
    	FIRE(2),	//火
    	WOOD(3),	//木
    	LIGHT(4),	//光
    	DARK(5),	//暗
    	All(6);	//全属性
    	private int value;
    	private Nature(int value){
    		this.value = value;
    	}
    	
    	public int getValue() {
			return value;
		}

		public static  boolean isNature(int nature){
    		for(Nature tmp : Nature.values()){
    			if(tmp.getValue() != All.value && tmp.getValue() == nature){
    				return true;
    			}
    		}
    		return false;
    	}
    }

	public static final class BattlePropType {
		public static final int Attack = 1;			//攻击
		public static final int Armor = 2;			//防御
		public static final int Hp = 3;				//血量
		public static final int MagicAttack = 4;	//魔强
		public static final int MagicArmor = 5;		//魔防
		public static final int AttackSpeed = 6;	//攻速
		public static final int Dodge = 7;			//闪避
		public static final int Crit = 8;			//暴击
		public static final int BlockHurt = 9;		//格挡
		
		public static final int AttackRate = 101;
		public static final int ArmorRate = 102;
		public static final int HpRate = 103;
		public static final int MagicAttackRate = 104; 
		public static final int MagicArmorRate = 105;
		public static final int AttackSpeedRate = 106;
		public static final int DodgeRate = 107;
		public static final int CritRate = 108;
		public static final int BlockHurtRate = 109;
		
		public static final int MainPropRate = 1001;	//主属性（攻击、防御、血量、魔强、魔防）加成比例
		public static final int AllPropRate = 1002;	//全属性（攻击、防御、血量、魔强、魔防、攻速、闪避、暴击、格挡）加成比例
	}
	
	public static final class BattlePropKey {
		public static final String Attack = "attack";
		public static final String Armor = "armor";
		public static final String Hp = "hp";
		public static final String MagicAttack = "magicAttack";
		public static final String MagicArmor = "magicArmor";
		public static final String AttackSpeed = "attackSpeed";
		public static final String Dodge = "dodge";
		public static final String Crit = "crit";
		public static final String BlockHurt = "blockHurt";
		
		public static final String AttackRate = "attackRate";
		public static final String ArmorRate = "armorRate";
		public static final String HpRate = "hpRate";
		public static final String MagicAttackRate = "magicAttackRate"; 
		public static final String MagicArmorRate = "magicArmorRate";
		public static final String AttackSpeedRate = "attackSpeedRate";
		public static final String DodgeRate = "dodgeRate";
		public static final String CritRate = "critRate";
		public static final String BlockHurtRate = "blockHurtRate";
		
		public static final String MainPropRate = "mainPropRate";
		public static final String AllPropRate = "allPropRate";
	}
	
	public static final class BattlePropAwakeKey{
		public static final String AttackLevel = "attackLevel";
		public static final String ArmorLevel = "armorLevel";
		public static final String HpLevel = "hpLevel";
		public static final String MagicAttackLevel = "magicAttackLevel";
		public static final String MagicArmorLevel = "magicArmorLevel";
	}
	
	public static final class AgentBattleType {
		public static final int Offensive = 1;	//进攻型
		public static final int Defensive = 2;	//防御型
		public static final int Assisted = 3;	//辅助型
	}
	
	public static final class DeviceFlag {
		public static final int IOS = 1;
		public static final int Android = 2;
		public static final int Third = 0;
	}
	
	public static final class DeviceName {
		public static final String IOS = "ios";
		public static final String Android = "android";
		public static final String Third = "third";
	}
	
	public static final class Charactar {
		public static final int Hero = 1;
		public static final int Eidonlon = 2;
	}
	
	public static final class ShopType {
		public static final int NormalShop = 1;//普通商店
		public static final int SocietyShop = 2;//公会商店
		public static final int ArenaShop = 3;//竞技商店
	}
	
	public static final class RewardType {
		public static final String Item = "item";
		public static final String Prop = "prop";
	}
	
	public static final class SocietyBossActiveStatus {
		public static final int NotOpen = 0;//未开启
		public static final int Open = 1;//已开启
		public static final int End = 2;//已结束
	}
	
	public static final class SocietyBattleStage {
		public static final int Sign = 1;//报名时间
		public static final int SignEnd = 2;//报名结束，未开始战斗
		public static final int Battle = 3;//已经开始战斗，战斗未结束
		public static final int BattleEnd = 4;//战斗已结束，等待发奖励
		public static final int RewardEnd = 5;//公会战已结束
	}
}
