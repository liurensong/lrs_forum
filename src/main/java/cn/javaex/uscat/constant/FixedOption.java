package cn.javaex.uscat.constant;

/**
 * 固定选项类
 */
public class FixedOption {
	public enum DefaultOrderField {
		REPLY_TIME("reply_time", "回复时间"),
		CREATE_TIME("create_time", "发布时间"),
		REPLY_COUNT("reply_count", "回复数量"),
		VIEW_COUNT("view_count", "浏览次数")
		;
		
		private String value;
		private String name;
		private DefaultOrderField(String value, String name) {
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static void main(String[] args) {
		// 输出某一枚举的值
		System.out.println(DefaultOrderField.REPLY_TIME.getValue());
		System.out.println(DefaultOrderField.REPLY_TIME.getName());
		
		// 遍历所有的枚举
		for (DefaultOrderField opt : DefaultOrderField.values()) {
			System.out.println(opt + "		value:" + opt.getValue() + "  name:" + opt.getName());
		}
	}
}
