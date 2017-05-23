package com.benjie.legend.client.vo;


public class Position {

	private int spaceId;
	private String pos;
	private String direction;
	private int dancing;
	private int moveState = 1;// 当前状态
	private long nextMoveTime;
	private long stopTime;
	private int moveType;
	private String targetPos;
	private float stopDis;
	private String aniName;
	private int jumpState;

	public Position(int spaceId, String pos, String direction, int dancing, int moveType, String targetPos, float stopDis) {
		this.spaceId = spaceId;
		this.pos = pos;
		this.direction = direction;
		this.dancing = dancing;
		this.moveType = moveType;
		this.targetPos = targetPos;
		this.stopDis = stopDis;
		this.aniName = "";
	}

	public int getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getMoveType() {
		return moveType;
	}

	public void setMoveType(int moveType) {
		this.moveType = moveType;
	}

	public int getMoveState() {
		return moveState;
	}

	public void setMoveState(int moveState) {
		this.moveState = moveState;
	}

	public long getNextMoveTime() {
		return nextMoveTime;
	}

	public void setNextMoveTime(long nextMoveTime) {
		this.nextMoveTime = nextMoveTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public int getDancing() {
		return dancing;
	}

	public void setDancing(int dancing) {
		this.dancing = dancing;
	}

	public String getTargetPos() {
		return targetPos;
	}

	public void setTargetPos(String targetPos) {
		this.targetPos = targetPos;
	}

	public float getStopDis() {
		return stopDis;
	}

	public void setStopDis(float stopDis) {
		this.stopDis = stopDis;
	}

	public String getAniName() {
		return aniName;
	}

	public void setAniName(String aniName) {
		this.aniName = aniName;
	}

	public int getJumpState() {
		return jumpState;
	}

	public void setJumpState(int jumpState) {
		this.jumpState = jumpState;
	}

}
