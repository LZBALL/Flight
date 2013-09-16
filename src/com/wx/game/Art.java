package com.wx.game;
import java.util.ArrayList;

import android.graphics.Rect;

public class Art {
	public Rect spriteFrame = null;
	
	public int speedX = 0;
	public int speedY = 0;
	
	public int maxHP = 0;
	public int HP = 0;
	
	public int ATK = 0;
	
	public String sprite = null;
	
	public ArtState artState = null;
	public ArtState artNormalState = null;
	public ArtState artBombingState = null;
	public ArtState artAttackedState = null;
	
	public ArtFactory artFactor = null;
	
	public GameContext gameContext = null;
	
	public int score = 0;
	
	/* ���ý�ɫ״̬����Ѫ�����ٶȵȡ����ڶ�`����`��ɫ������
	 * 
	 */
	public void reset() {
		// ��ʼ��˳���Ӱ���ɫ��һ�γ���ʱ����ͼ
		if (this.artBombingState != null) {
			this.artBombingState.reset();
		}
		
		if (this.artAttackedState != null) {
			this.artAttackedState.reset();
		}
		
		if (this.artNormalState != null) {
			this.artNormalState.reset();
		}
		
		this.artState = this.artNormalState;
		
		this.HP = this.maxHP;
	}
	
	/* ���ն����Ա�����
	 * 
	 */
	public void reuse() {
		if (artFactor != null) {
			artFactor.reuseArt(this);
		}
	}
	
	/* ��ɫ��ǰ�ڵ�ͼ�е�λ��
	 * 
	 */
	public Rect spriteFrame() {
		return this.spriteFrame;
	}
	
	/* �ƶ���ɫһ�ξ���
	 * param:dx ˮƽƫ��
	 * param:dy ��ֱƫ��
	 */
	public void move(int dx, int dy) {
		if (this.artState != null) {
			this.artState.move(dx, dy);
		}
	}
	
	/* �ƶ���ɫ
	 * 
	 */
	public void move(float deltaTime) {
		if (this.artState != null) {
			this.artState.move(deltaTime);
		}
	}
	
	/* �ƶ���ָ��λ��
	 * 
	 */
	void moveTo(int x, int y) {
		if (this.artState != null) {
			this.artState.moveTo(x, y);
		}
	}
	
	/* �Ƿ����ɫa��ײ
	 * 
	 */
	public boolean isCollision(Art a) {
		return (this.artState != null && this.artState.isCollision(a));
	}
	
	/*  ����ɫa����
	 * 
	 */
	public void beAttacked(Art a) {
		beAttacked(a.ATK);
	}
	
	/* ��attack��С�Ĺ���������
	 * 
	 */
	public void beAttacked(int attack) {
		if (this.artState != null) {
			this.artState.beAttacked(attack);
		}
	}
	
	void changeState(ArtState s) {
		this.artState = s;
	}
	
	public boolean isNormalState() {
		return (this.artState != null && this.artState == this.artNormalState);
	}
	
	public boolean isAttackingState() {
		return (this.artState != null && this.artState == this.artAttackedState);
	}
	
	public boolean isBombingState() {
		return (this.artState != null && this.artState == this.artBombingState);
	}
	
	public boolean isDestroyState() {
		return (this.artState == null);
	}
	
	public void draw(float deltaTime) {
		if (this.artState != null) {
			this.artState.draw(deltaTime);
		}
	}
	
	void displaySprite() {
		if (this.sprite != null) {
			this.gameContext.displayArt(this);
		}
	}
	
	/* ��ɫ״̬����
	 * 
	 */
	class ArtState {
		ArrayList<String> sprites = new ArrayList<String>(); /* ��ɫ��ǰ״̬����ͼ���� */
		int index = 0;     /* sprites�������� */
		int tickCount = 0; /* ִ�д��� */
		float tick = 0.1f; /* �೤ʱ��ִ��һ�� */
		float time = 0.0f; /* ִ��ʱ�� */
		
		/* ���õ�ǰ״̬
		 *  
		 */
		void reset() {
			index = 0;
			tickCount = 0;
			time = 0.0f;
			tick = 0.1f;
			
			sprite = nextTickSprite();
		}
		
		void move(int dx, int dy) {
			if (spriteFrame != null) {
				spriteFrame.offset(dx, dy);
				if (spriteFrame.top > gameContext.VBottom()) {
					changeState(null); // ��ɫ�ƶ�����Ļ�ײ���Ĭ�����ٽ�ɫ���ӵ���Ӣ��������ʵ�֣�
				}
			}
		}
		
		void moveTo(int x, int y) {
			if (spriteFrame != null) {
				spriteFrame.offsetTo(x, y);
			}
		}

		void move(float deltaTime) {
			this.move((int)(speedX*deltaTime), (int)(speedY*deltaTime));
		}
		
		boolean isCollision(Art a) {
			if (spriteFrame == null) {
				return false;
			}
			
			return Rect.intersects(spriteFrame, a.spriteFrame);
		}
		
		void beAttacked(Art a) {
			beAttacked(a.ATK);
		}
		
		void beAttacked(int attack) {
			
		}
		
		void draw(float deltaTime) {
			displaySprite();
			
			if (nextTick(deltaTime)) {
				sprite = nextTickSprite();
			}
		}
		
		/* tickʱ��֮����ʾ��һ����ͼ
		 * 
		 */
		String nextTickSprite() {
			return null;
		}
		
		private boolean nextTick(float deltaTime) {
			time += deltaTime;
			if (time < tick) {
				return false;
			}
			time -= tick;
			
			tickCount++;
			index ++;
			return true;
		}
	}
}
