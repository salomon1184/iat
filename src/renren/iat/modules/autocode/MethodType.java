package renren.iat.modules.autocode;

public enum MethodType {
	Get((byte) 1), Post((byte) 2), Both((byte) 3);
	private final byte value;

	public byte getValue() {
		return this.value;
	}

	MethodType(byte value) {
		this.value = value;
	}

	public static MethodType parse(byte value) {
		MethodType retValue = MethodType.Both;
		for (MethodType item : MethodType.values()) {
			if (value == item.getValue()) {
				retValue = item;
				break;
			}
		}

		return retValue;
	}
}
