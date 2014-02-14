package cn.itcast.pojo;

/**
 * Goods entity. @author MyEclipse Persistence Tools
 */

public class Goods implements java.io.Serializable {

	private Integer gid;
	private String gname;
	private Double gprice;
	private String gpic;
	private String gremark;
	private String gxremark;
	private Category category;

	// Constructors

	/** default constructor */
	public Goods() {
	}

	/** full constructor */
	public Goods(Category category, String gname, Double gprice, String gpic,
			String gremark, String gxremark) {
		this.category = category;
		this.gname = gname;
		this.gprice = gprice;
		this.gpic = gpic;
		this.gremark = gremark;
		this.gxremark = gxremark;
	}

	// Property accessors

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getGname() {
		return this.gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public Double getGprice() {
		return this.gprice;
	}

	public void setGprice(Double gprice) {
		this.gprice = gprice;
	}

	public String getGpic() {
		return this.gpic;
	}

	public void setGpic(String gpic) {
		this.gpic = gpic;
	}

	public String getGremark() {
		return this.gremark;
	}

	public void setGremark(String gremark) {
		this.gremark = gremark;
	}

	public String getGxremark() {
		return this.gxremark;
	}

	public void setGxremark(String gxremark) {
		this.gxremark = gxremark;
	}

}