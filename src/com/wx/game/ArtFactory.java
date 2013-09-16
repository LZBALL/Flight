package com.wx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/* ��ɫ�����������ɫ����������������
 * 
 */
public class ArtFactory {
	ArrayList<Art> reuseArtList = new ArrayList<Art>();
	Class<?> artClass = null;
	float time = 0.0f;
	float tick = 3.5f;
	float ORI_TICK = 0.0f;
	Random rand = new Random();
	public Iterator<Art> artItr = null;
	int salt = 0; // ����
	
	/* ��ȡ�µĽ�ɫ��������new������ȫ�¶���Ҳ�����ǻ��պ�Ķ�������״̬�������ã�
	 * 
	 */
	public Art genareteArt(GameContext ctx, float deltaTime) {
		time += deltaTime;
		if (time < tick) {
			return null;
		}
		time -= tick;
		if (salt > 0) {
			tick = ORI_TICK + (float)(rand.nextInt(salt)/1000.0);
		}
		
		return getArt(ctx);
	}
	
	/* ʵ��������
	 * param: cls �����Ķ�������
	 * param: tick �೤ʱ�䴴������һ������
	 * param: salt tick����һ������� 
	 */
	public ArtFactory(Class<?> cls, float tick, int salt) {
		this.artClass = cls;
		this.ORI_TICK = tick;
		this.tick = tick;
		this.salt = salt;
	}
	
	/* ���ն���
	 * 
	 */
	public void reuseArt(Art a) {
		reuseArtList.add(a);
	}
	
	private Art getArt(GameContext ctx)  {
		Art a = null;
		try {
			if (this.reuseArtList.size() > 0) {
				a = this.reuseArtList.remove(0);
			} else {
				a = (Art)this.artClass.newInstance();
				a.artFactor = this;
				a.gameContext = ctx;
			}
			a.reset();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return a;
	}
}
