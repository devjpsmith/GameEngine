package sprites;

public abstract class Drawable {
	protected int mSourceTilesAcross;
	protected int mWidth;
	protected int mHeight;
	protected int mGid;
	
	public int getSourceX(){
		return (((mGid - 1) % mSourceTilesAcross) * mWidth);
	}
	
	public int getSourceY(){
		return (Math.floorDiv(mGid - 1, mSourceTilesAcross)) * mHeight;
	}
}
