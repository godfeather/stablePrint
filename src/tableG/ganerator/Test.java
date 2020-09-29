package tableG.ganerator;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		TableFormat tf=new TableFormat();
		tf.getTableFormat(tf.comp("утндЁ╛\\ё╛ут\\,"));
		tf.showTable();
	}
}
