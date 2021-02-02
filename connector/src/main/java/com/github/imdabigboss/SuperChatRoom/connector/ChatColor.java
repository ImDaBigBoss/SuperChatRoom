package com.github.imdabigboss.SuperChatRoom.connector;

public enum ChatColor {
	BLACK("0"),
	DARK_BLUE("1"),
	DARK_GREEN("2"),
	DARK_AQUA("3"),
	DARK_RED("4"),
	DARK_PURPLE("5"),
	GOLD("6"),
	GRAY("7"),
	DARK_GRAY("8"),
	BLUE("9"),
	GREEN("a"),
	AQUA("b"),
	RED("c"),
	LIGHT_PURPLE("d"),
	YELLOW("e"),
	WHITE("f"),
	RESET("r");
	
	public final String label;

    private ChatColor(String label) {
        this.label = "\u00A7" + label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
