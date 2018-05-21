

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;

public class FlexibleLayout implements LayoutManager2 {

	public static final int ROW_TYPE_SSSS = 1;
	public static final int ROW_TYPE_MM = 2;
	public static final int ROW_TYPE_L = 3;
	public static final int ROW_TYPE_MSS = 4;
	public static final int ROW_TYPE_SSM = 5;
	
	private static final int COL_NUM = 4;
	
	private int rowNum;
	
	private int hGap = 3;
	private int vGap = 3;
	
	private Component[][] bodis;
	private Component[][] heads;
	private int[] rowTypes;
	
	public FlexibleLayout() {
		initilazie();
	}

	
	private void initilazie() {
		bodis = new Component[rowNum][COL_NUM];
		heads = new Component[rowNum][COL_NUM];
		rowTypes = new int[rowNum];
		// TODO a meglevo adatokat at kellene menteni
	}
	
	/***********************************************
	 * <INTERFACE>
	 * *********************************************/
	
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	public void invalidateLayout(Container target) { }

	public Dimension maximumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0,0);
			Dimension localDim = new Dimension(0,0); 

			Dimension c = null;
			
			for(int row=0; row<rowNum; row++) {
				localDim.width = 0;
				localDim.height = 0;
				for(int col=0; col<COL_NUM; col++) {
					c = getItemMaxSize(row, col);
					if(c!=null) {
						localDim.width += c.width;
						localDim.height = Math.max(localDim.height, c.height);
					}
				}
				dim.width = Math.max(dim.width,localDim.width);
				dim.height += localDim.height;
			}
			
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right;
			dim.height += insets.top + insets.bottom;

//			// System.out.println("maximumLayoutSize: " + dim);
			
			return dim;
		}
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		 synchronized (comp.getTreeLock()) {
//			 // System.out.println("addLayoutComponent [" + comp +" ]/[ " + constraints + "]");
			if (constraints == null) {
				constraints = new FlexibleDefinitor(0,0,false);
			}

			if (constraints instanceof Point) {
				Point p = (Point)constraints;
				constraints = new FlexibleDefinitor(p.x,p.y);
			}
			
			if (constraints instanceof FlexibleDefinitor) {
				FlexibleDefinitor coord = (FlexibleDefinitor) constraints;
				if(coord.getCol() >= COL_NUM || coord.getRow() >= rowNum) {
					throw new IllegalArgumentException("cannot add to layout: location is outside from range " + constraints);
				}
        if(coord.isBody()) {
        	bodis[coord.getRow()][coord.getCol()] = comp;
        } else {
        	heads[coord.getRow()][coord.getCol()] = comp;
        }
			} else {
			    throw new IllegalArgumentException("cannot add to layout: unknown location: " + constraints);
			}
		}
	}

	public void removeLayoutComponent(Component comp) {
		synchronized (comp.getTreeLock()) {
			// System.out.println("removeLayoutComponent: " + comp);
			for(int x=0; x<rowNum; x++) {
				for(int y=0; y<COL_NUM; y++) {
					if(bodis[x][y]==comp) {
						bodis[x][y] = null;
					}
					if(heads[x][y] == comp) {
						heads[x][y] = null;
					}
				}
			}
		}
	}

	public void layoutContainer(Container parent) {
		  synchronized (parent.getTreeLock()) {
			  // System.out.println("layoutContainer");
			  
				Insets insets = parent.getInsets();
				int top = insets.top;
				int bottom = parent.getSize().height - insets.bottom;
				int left = insets.left;
				int right = parent.getSize().width - insets.right;
//				Dimension maxPref = getMaximumPrefSize();
				
				int maxHeight = 0; 
				for(int i=0; i<rowNum; i++) {
					maxHeight += getMaximumPrefHeight(i);
				}
				int heightResidue = (bottom - top - maxHeight)/rowNum;
				
				MaxValueHolder max = calculateMaxValues(left, right);
								
				int actualHeight = 0;
				Dimension actDim = null;
				
				int[] maxHeadPref = getMaximumHeadPrefWidth();

				int l = 0;
				Component c1 = null;
				Component c2 = null;
				Component c3 = null;
				Component c4 = null;
				
				Component h = null;
				
				for(int row=0; row<rowNum; row++) {
					actualHeight = getMaximumPrefHeight(row) + heightResidue;
					if(row == rowNum - 1) {
						actualHeight = bottom - top;
					}
					c1 = bodis[row][0];
					c2 = bodis[row][1];
					c3 = bodis[row][2];
					c4 = bodis[row][3];
					
					switch(rowTypes[row]) {
						case ROW_TYPE_SSSS :
							if(c1 != null) {
								h = heads[row][0];
								if(h != null) {
									l = maxHeadPref[0];
									c1.setSize(max.ssss[0]-maxHeadPref[0],actualHeight);
									h.setBounds(left,top,maxHeadPref[0],actualHeight);
								} else {
									l = left;
									c1.setSize(max.ssss[0],actualHeight);
								}
								actDim = c1.getSize();
								c1.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c2 != null) {
								h = heads[row][1];
								if(h != null) {
									l = left + max.ssss[0] + maxHeadPref[1];
									c2.setSize(max.ssss[1]-maxHeadPref[1],actualHeight);
									h.setBounds(left + max.ssss[0],top,maxHeadPref[1],actualHeight);
								} else {
									l = left + max.ssss[0];
									c2.setSize(max.ssss[1],actualHeight);
								}
								actDim = c2.getSize();
								c2.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c3 != null) {
								h = heads[row][2];
								if(h != null) {
									l = left + max.ssss[0] + max.ssss[1] + maxHeadPref[2];
									c3.setSize(max.ssss[2]-maxHeadPref[2],actualHeight);
									h.setBounds(left + max.ssss[0] + max.ssss[1],top,maxHeadPref[2],actualHeight);
								} else {
									l = left + max.ssss[0] + max.ssss[1];
									c3.setSize(max.ssss[2],actualHeight);
								}
								actDim = c3.getSize();
								c3.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c4 != null) {
								h = heads[row][3];
								l = left + max.ssss[0] + max.ssss[1] + max.ssss[2];
								if(h != null) {
									h.setBounds(l,top,maxHeadPref[3],actualHeight);
									c4.setSize(max.ssss[3]-maxHeadPref[3],actualHeight);
									l += maxHeadPref[3];
								} else {
									c4.setSize(max.ssss[3],actualHeight);
								}
								actDim = c4.getSize();
								c4.setBounds(l,top,right-l,actDim.height);
							}
							break;
						case ROW_TYPE_MM :
							if(c1 != null) {
								h = heads[row][0]; 
								l = left;
								if(h!=null) {
									c1.setSize(max.mm[0]-maxHeadPref[0],actualHeight);
									h.setBounds(l,top,maxHeadPref[0],actualHeight);
									l += maxHeadPref[0];
								} else {
									c1.setSize(max.mm[0],actualHeight);
								}
								actDim = c1.getSize();
								c1.setBounds(l,top,actDim.width,actDim.height);
								
							}
							if(c3 != null) {
								h = heads[row][2]; 
								l = left + max.mm[0];
								if(h!=null) {
									c3.setSize(max.mm[1]-maxHeadPref[2],actualHeight);
									h.setBounds(l,top,maxHeadPref[2],actualHeight);
									l += maxHeadPref[2];
								} else {
									c3.setSize(max.mm[1],actualHeight);
								}
								actDim = c3.getSize();
								c3.setBounds(l,top,right - l,actDim.height);
							}
							break;
						case ROW_TYPE_L :
							if(c1 != null) {
								h = heads[row][0]; 
								l = left;
								if(h!=null) {
									c1.setSize(max.l-maxHeadPref[0],actualHeight);
									h.setBounds(l,top,maxHeadPref[0],actualHeight);
									l += maxHeadPref[0];
								} else {
									c1.setSize(max.l,actualHeight);
								}
								actDim = c1.getSize();
								c1.setBounds(l,top,actDim.width,actDim.height);
							}
							break;
						case ROW_TYPE_MSS :
							if(c1 != null) {
								h = heads[row][0]; 
								l = left;
								if(h!=null) {
									c1.setSize(max.mm[0]-maxHeadPref[0],actualHeight);
									h.setBounds(l,top,maxHeadPref[0],actualHeight);
									l += maxHeadPref[0];
								} else {
									c1.setSize(max.mm[0],actualHeight);
								}
								actDim = c1.getSize();
								c1.setBounds(l,top,actDim.width,actDim.height);
								
							}
							if(c3 != null) {
								h = heads[row][2];
								if(h != null) {
									l = left + max.ssss[0] + max.ssss[1] + maxHeadPref[2];
									c3.setSize(max.ssss[2]-maxHeadPref[2],actualHeight);
									h.setBounds(left + max.ssss[0] + max.ssss[1],top,maxHeadPref[2],actualHeight);
								} else {
									l = left + max.ssss[0] + max.ssss[1];
									c3.setSize(max.ssss[2],actualHeight);
								}
								actDim = c3.getSize();
								c3.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c4 != null) {
								h = heads[row][3];
								l = left + max.ssss[0] + max.ssss[1] + max.ssss[2];
								if(h != null) {
									h.setBounds(l,top,maxHeadPref[3],actualHeight);
									c4.setSize(max.ssss[3]-maxHeadPref[3],actualHeight);
									l += maxHeadPref[3];
								} else {
									c4.setSize(max.ssss[3],actualHeight);
								}
								actDim = c4.getSize();
								c4.setBounds(l,top,right-l,actDim.height);
							}
							break;
						case ROW_TYPE_SSM :
							if(c1 != null) {
								h = heads[row][0];
								l = left;
								if(h != null) {
									c1.setSize(max.ssss[0]-maxHeadPref[0],actualHeight);
									h.setBounds(left,top,maxHeadPref[0],actualHeight);
									l += maxHeadPref[0];
								} else {
									c1.setSize(max.ssss[0],actualHeight);
								}
								actDim = c1.getSize();
								c1.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c2 != null) {
								h = heads[row][1];
								if(h != null) {
									l = left + max.ssss[0] + maxHeadPref[1];
									c2.setSize(max.ssss[1]-maxHeadPref[1],actualHeight);
									h.setBounds(left + max.ssss[0],top,maxHeadPref[1],actualHeight);
								} else {
									l = left + max.ssss[0];
									c2.setSize(max.ssss[1],actualHeight);
								}
								actDim = c2.getSize();
								c2.setBounds(l,top,actDim.width,actDim.height);
							}
							if(c3 != null) {
								h = heads[row][2]; 
								l = left + max.mm[0];
								if(h!=null) {
									c3.setSize(max.mm[1]-maxHeadPref[2],actualHeight);
									h.setBounds(l,top,maxHeadPref[2],actualHeight);
									l += maxHeadPref[2];
								} else {
									c3.setSize(max.mm[1],actualHeight);
								}
								actDim = c3.getSize();
								c3.setBounds(l,top,right - l,actDim.height);
							}
							break;
					}
					top += actualHeight;
					
				}
				
		  }
	}

	/**
	 * @deprecated
	 */
	public void addLayoutComponent(String name, Component comp) {
		throw new IllegalArgumentException("cannot add to layout: this method not suit for this layout manager");
	}

	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Dimension dim = new Dimension(0,0);
			Dimension localDim = new Dimension(0,0); 
			
			Dimension c = null;
			
			for(int row=0; row<rowNum; row++) {
				localDim.width = 0;
				localDim.height = 0;
				for(int col=0; col<COL_NUM; col++) {
					c = getItemMinSize(row,col);
					if(c!=null) {
						localDim.width += c.width;
						localDim.height = Math.max(localDim.height, c.height);
					}
				}
				dim.width = Math.max(dim.width,localDim.width);
				dim.height += localDim.height;
			}
			
			Insets insets = parent.getInsets();
			dim.width += insets.left + insets.right;
			dim.height += insets.top + insets.bottom;

			// System.out.println("minimumLayoutSize: " + dim);
			
			return dim;
		}
	}

	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Dimension dim = new Dimension(0,0);
			Dimension localDim = new Dimension(0,0); 
			
			Dimension c = null;
			
			for(int row=0; row<rowNum; row++) {
				localDim.width = 0;
				localDim.height = 0;
				for(int col=0; col<COL_NUM; col++) {
					c = getItemPrefSize(row,col);
					if(c!=null) {
						localDim.width += c.width;
						localDim.height = Math.max(localDim.height, c.height);
					}
				}
				dim.width = Math.max(dim.width,localDim.width);
				dim.height += localDim.height;
				
				// System.out.println(dim);
			}
			
			dim.width = Math.max(dim.width,calculateMaxValues(0,0).l);
			
			Insets insets = parent.getInsets();
			dim.width += insets.left + insets.right;
			dim.height += insets.top + insets.bottom;

			// System.out.println("preferredLayoutSize: " + dim);
			
			return dim;
		}
	}

	/***********************************************
	 * </INTERFACE>
	 * *********************************************/
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
		initilazie();
	}
	
	public void setRowType(int row, int type) {
		try {
			if(rowTypes != null) {
				rowTypes[row] = type;
			} else {
				throw new IllegalArgumentException("cannot set the row's type: the row set is empty");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("cannot set the row's type: maximum row's number is " + rowNum);
		}
	}
	
//	private int getMaximumPrefWidth() {
//		int w = 0;
//		int tmp = 0;
//		Dimension c = null;
//		for(int row=0; row<rowNum; row++) {
//			tmp = 0;
//			for(int col=0; col<COL_NUM; col++) {
//				c = getItemMinSize(row,col);
//				if(c!=null) {
//					tmp += c.width;
//				}
//			}
//			w = Math.max(w,tmp);
//		}
//		return w;
//	}
	
	private int[] getMaximumHeadPrefWidth() {
		int[] w = new int[COL_NUM];
		for(int i=0; i<COL_NUM; ) {
			w[i++]=0;
		}
		Component c = null;
		for(int row=0; row<rowNum; row++) {
			for(int col=0; col<COL_NUM; col++) {
				c = heads[row][col];
				if(c != null) {
					w[col] = Math.max(w[col],c.getPreferredSize().width);
				}
			}
		}
		
		return w;
	}
	
	private Dimension getItemPrefSize(int row, int col) {		
		Component body = bodis[row][col];
		Component head = heads[row][col];
		if(body == null && head == null) return null;
		Dimension dim = new Dimension();
		
		dim.width = (body != null ? body.getPreferredSize().width : 0) + (head != null ? head.getPreferredSize().width : 0);
		dim.height = Math.max(body != null ? body.getPreferredSize().height  : 0, (head != null ? head.getPreferredSize().height : 0));

		// System.out.println("("+row+","+col+")"+dim);
		
		return dim;
	}
	
	private Dimension getItemMinSize(int row, int col) {
		Component body = bodis[row][col];
		Component head = heads[row][col];
		if(body == null && head == null) return null;
		Dimension dim = new Dimension();
		
		dim.width = (body != null ? body.getMinimumSize().width : 0) + (head != null ? head.getMinimumSize().width : 0);
		dim.height = Math.max(body != null ? body.getMinimumSize().height  : 0, (head != null ? head.getMinimumSize().height : 0));
		
		return dim;
	}
	
	private Dimension getItemMaxSize(int row, int col) {
		Component body = bodis[row][col];
		Component head = heads[row][col];
		if(body == null && head == null) return null;
		Dimension dim = new Dimension();
		
		dim.width = (body != null ? body.getMaximumSize().width : 0) + (head != null ? head.getMaximumSize().width : 0);
		dim.height = Math.max(body != null ? body.getMaximumSize().height  : 0, (head != null ? head.getMaximumSize().height : 0));
		
		return dim;
	}
	
//	private Dimension getItemSize(int row, int col) {
//		Component body = bodis[row][col];
//		Component head = heads[row][col];
//		if(body == null && head == null) return null;
//		Dimension dim = new Dimension();
//		
//		dim.width = (body != null ? body.getSize().width : 0) + (head != null ? head.getSize().width : 0);
//		dim.height = Math.max(body != null ? body.getSize().height  : 0, (head != null ? head.getSize().height : 0));
//		
//		return dim;
//	}
	
	private int getMaximumPrefHeight(int row) {
		int h = 0;
		Dimension c = null;
		for(int col=0; col<COL_NUM; col++) {
			c = getItemPrefSize(row,col);
			if(c!=null) {
				h = Math.max(h,c.height);
			}
		}
		return h;
	}
	
//	private Dimension getMaximumPrefSize() {
//		Dimension dim = new Dimension(0,0);
//		Dimension localDim = new Dimension(0,0); 
//		
//		Component c = null;
//		
//		for(int row=0; row<rowNum; row++) {
//			localDim.width = 0;
//			localDim.height = 0;
//			for(int col=0; col<COL_NUM; col++) {
//				c = bodis[row][col];
//				if(c!=null) {
//					localDim.width += c.getPreferredSize().width;
//					localDim.height = Math.max(localDim.height, c.getPreferredSize().height);
//				}
//			}
//			dim.width = Math.max(dim.width,localDim.width);
//			dim.height += localDim.height;
//		}
//		
//		return dim;
//	}


	public int getHGap() {
		return hGap;
	}


	public void setHGap(int gap) {
		hGap = gap;
	}


	public int getVGap() {
		return vGap;
	}


	public void setVGap(int gap) {
		vGap = gap;
	}
	
	private MaxValueHolder calculateMaxValues(int left, int right) {
		int[] maxSSSS = new int[]{0,0,0,0};
		int[] maxMM = new int[]{0,0};
		int maxL = 0;
		
		Dimension d1 = null;
		Dimension d2 = null;
		Dimension d3 = null;
		Dimension d4 = null;
		
		int[] mH = getMaximumHeadPrefWidth();
		
		for(int row=0; row<rowNum; row++) {
			d1 = getItemPrefSize(row,0);
			d2 = getItemPrefSize(row,1);
			d3 = getItemPrefSize(row,2);
			d4 = getItemPrefSize(row,3);
			
			if(d1 != null) {
				d1.width = Math.max(d1.width,
						mH[0] + (bodis[row][0] != null ? bodis[row][0].getPreferredSize().width : 0));
			}
			if(d2 != null) {
				d2.width = Math.max(d2.width,
						mH[1] + (bodis[row][1] != null ? bodis[row][1].getPreferredSize().width : 0));
			}
			if(d3 != null) {
				d3.width = Math.max(d3.width,
						mH[2] + (bodis[row][2] != null ? bodis[row][2].getPreferredSize().width : 0));
			}
			if(d4 != null) {
				d4.width = Math.max(d4.width,
						mH[3] + (bodis[row][3] != null ? bodis[row][3].getPreferredSize().width : 0));
			}
			
			switch(rowTypes[row]) {
				case ROW_TYPE_SSSS :
					if(d1 != null) {
						maxSSSS[0] = Math.max(maxSSSS[0],d1.width);
					}
					if(d2 != null) {
						maxSSSS[1] = Math.max(maxSSSS[1],d2.width);
					}
					if(d1 != null && d2 != null) {
						maxMM[0] = Math.max(maxMM[0],d1.width + d2.width);
					}
					if(d3 != null) {
						maxSSSS[2] = Math.max(maxSSSS[2],d3.width);
					}
					if(d4 != null) {
						maxSSSS[3] = Math.max(maxSSSS[3],d4.width);
					}
					if(d3 != null && d4 != null) {
						maxMM[1] = Math.max(maxMM[0],d3.width + d4.width);
					}
					if(d1 != null && d2 != null && d3 != null && d4 != null) {
						maxL = Math.max(maxL,d1.width + d2.width + d3.width + d4.width);
					}
					break;
				case ROW_TYPE_MM : 
					if(d1 != null) {
						maxMM[0] = Math.max(maxMM[0],d1.width);
					}
					if(d3 != null) {
						maxMM[1] = Math.max(maxMM[1],d3.width);
					}
					if(d1 != null && d3 != null) {
						maxL = Math.max(maxL,d1.width + d3.width);
					}
					break;
				case ROW_TYPE_L :
					if(d1 != null) {
						maxL = Math.max(maxL,d1.width);
					}
					break;
				case ROW_TYPE_MSS : 
					if(d1 != null) {
						maxMM[0] = Math.max(maxMM[0],d1.width);
					}
					if(d3 != null) {
						maxSSSS[2] = Math.max(maxSSSS[2],d3.width);
					}
					if(d4 != null) {
						maxSSSS[3] = Math.max(maxSSSS[3],d4.width);
					}
					if(d3 != null && d4 != null) {
						maxMM[1] = Math.max(maxMM[1],d3.width + d4.width);
					}
					if(d1 != null && d3 != null && d4 != null) {
						maxL = Math.max(maxL,d1.width + d3.width + d4.width);
					}
					break;
				case ROW_TYPE_SSM : 
					if(d1 != null) {
						maxSSSS[0] = Math.max(maxSSSS[0],d1.width);
					}
					if(d2 != null) {
						maxSSSS[1] = Math.max(maxSSSS[1],d2.width);
					}
					if(d1 != null && d2 != null) {
						maxMM[0] = Math.max(maxMM[0],d1.width + d2.width);
					}
					if(d3 != null) {
						maxMM[1] = Math.max(maxMM[1],d3.width);
					}
					
					if(d1 != null && d2 != null && d3 != null) {
						maxL = Math.max(maxL,d1.width + d2.width + d3.width);
					}
					break;
			}
			
		}
		
		int widthResidue = 0;
		
		maxL = Math.max(maxL,right - left);
		
		if(maxMM[0] + maxMM[1] < maxL) {
			widthResidue = (maxL - maxMM[0] - maxMM[1]) / 2;
			maxMM[0] += widthResidue;
			maxMM[1] += widthResidue;
		} 
		if(maxSSSS[0] + maxSSSS[1] < maxMM[0]) {
			widthResidue = (maxMM[0] - maxSSSS[0] - maxSSSS[1]) / 2;
			maxSSSS[0] += widthResidue;
			maxSSSS[1] += widthResidue;
		} 
		if(maxSSSS[2] + maxSSSS[3] < maxMM[1]) {
			widthResidue = (maxMM[1] - maxSSSS[2] - maxSSSS[3]) / 2;
			maxSSSS[2] += widthResidue;
			maxSSSS[3] += widthResidue;
		}
		
		if(maxSSSS[2] + maxSSSS[3] > maxMM[1]) {
			maxMM[1] = maxSSSS[2] + maxSSSS[3];
		}
		if(maxSSSS[0] + maxSSSS[1] > maxMM[0]) {
			maxMM[0] = maxSSSS[0] + maxSSSS[1]; 
		}
		if(maxMM[0] + maxMM[1] > maxL) {
			maxL = maxMM[0] + maxMM[1]; 
		}
		
		
		return new MaxValueHolder(maxL, maxMM, maxSSSS);
	}
	
	/*********************************
	 * Helper classes ******************
	 *********************************
	 */
	private class MaxValueHolder {
		int[] ssss = new int[]{0,0,0,0};
		int[] mm = new int[]{0,0};
		int l = 0;
		/**
		 * @param l
		 * @param mm
		 * @param ssss
		 */
		public MaxValueHolder(int l, int[] mm, int[] ssss) {
			// TODO Auto-generated constructor stub
			this.l = l;
			this.mm = mm;
			this.ssss = ssss;
		}
		
		public String toString() {
			return "max.l: " + l + "; max.mm: " + mm[0] + "," + mm[1] + "; max.ssss: " + ssss[0] + "," + ssss[1] + "," + ssss[2] + "," + ssss[3] + ";";
		}
		
	}
	
	public static class FlexibleDefinitor {
		
		private boolean head = false;
		private int row = -1;
		private int col = -1;
		
		/**
		 * @param row
		 * @param col
		 * @param head
		 */
		public FlexibleDefinitor(int row,int col, boolean head) {
			this.col = col;
			this.head = head;
			this.row = row;
		}
		
		public FlexibleDefinitor(int row,int col) {
			this.col = col;
			this.head = false;
			this.row = row;
		}
		
		public int getCol() {
			return col;
		}
		
		public boolean isHead() {
			return head;
		}
		
		public int getRow() {
			return row;
		}
		
		public boolean isBody() {
			return !head;
		}
		
		public String toString() {
			return getClass().getName() + "[" + row +","+col +"," +(head ? "H" : "B")+ "]";
		}
		
	}
	
}
