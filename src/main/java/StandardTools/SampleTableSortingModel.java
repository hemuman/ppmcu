/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package StandardTools;

/**
 *
 * @author Punam
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
class SampleSortingTableModel extends AbstractTableModel implements TableModelListener {
  protected TableModel base;
  protected int sortColumn;
  protected int[] row;
  public SampleSortingTableModel(TableModel tm, int sortColumn) {
    this.base = tm;
    this.sortColumn = sortColumn;
    tm.addTableModelListener(this);
    rebuild();
  }
  public Class getColumnClass(int c) {
    return base.getColumnClass(c);
  }
  public int getColumnCount() {
    return base.getColumnCount();
  }
  public String getColumnName(int c) {
    return base.getColumnName(c);
  }
  public int getRowCount() {
    return base.getRowCount();
  }
  public Object getValueAt(int r, int c) {
    return base.getValueAt(row[r], c);
  }
  public boolean isCellEditable(int r, int c) {
    return base.isCellEditable(row[r], c);
  }
  public void setValueAt(Object value, int r, int c) {
    base.setValueAt(value, row[r], c); // Notification will cause re-sort
  }
  public void tableChanged(TableModelEvent event) {
    rebuild();
  }
  protected void rebuild() {
    int size = base.getRowCount();
    row = new int[size];
    for (int i = 0; i < size; i++) {
      row[i] = i;
    }
    sort();
  }
  protected void sort() { // Sort and notify listeners
    for (int i = 1; i < row.length; i++) {
      int j = i;
      while (j > 0 && compare(j - 1, j) > 0) {
        int temp = row[j];
        row[j] = row[j - 1];
        row[j - 1] = temp;
        j--;
      }
    }
    fireTableStructureChanged();
  }
  protected int compare(int i, int j) {
    String s1 = base.getValueAt(row[i], sortColumn).toString();
    String s2 = base.getValueAt(row[j], sortColumn).toString();
    return s1.compareTo(s2);
  }
}
 class SortableTableModelAbstractTableModel extends JFrame {
  DefaultTableModel model = new DefaultTableModel(new Object[][] { { "this", "1" },
      { "text", "2" }, { "will", "3" }, { "be", "4" }, { "sorted", "5" } }, new Object[] {
      "Column 1", "Column 2" });
  public SortableTableModelAbstractTableModel() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JTable tableOrig = new JTable(model);
    tableOrig.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JTable tableSorted = new JTable(new SampleSortingTableModel(model, 1));
    tableSorted.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JPanel panel = new JPanel(new GridLayout(1, 2));
    panel.add(new JScrollPane(tableOrig));
    panel.add(new JScrollPane(tableSorted));
    getContentPane().add(panel, BorderLayout.CENTER);
    pack();
  }
  public static void main(String arg[]) {
    new SortableTableModelAbstractTableModel().setVisible(true);
  }
}




