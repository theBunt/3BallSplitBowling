package gui;

import db.MainProgramOperations;
import lib.TableColumnAdjuster;
import model.Alley;
import model.NumberValidator;
import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Peter on 06/03/2015.
 */
public class StaffTab extends JPanel implements ActionListener{

    private JPanel p1, p2, p1a;
    private JButton create, edit, refresh;
    private DefaultTableModel model;
    private JTable table;
    private MainProgramOperations progOps;
    private ArrayList<Staff> staffList = new ArrayList<Staff>();
    private String header[] = new String[] { "Staff Id", "Surname", "Name", "Phone"};
    private JTextField staffId, staffName;

    public StaffTab(ArrayList<Staff> s, MainProgramOperations po) {
        System.out.println("Inside : StaffTabGUI");
        this.progOps = po;
        this.staffList = s;

        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        p1a = new JPanel();
        p1a.setPreferredSize(new Dimension(180, 200));
        p1a.setLayout(new BoxLayout(p1a, BoxLayout.Y_AXIS));
        p1a.setBackground(Color.WHITE);
        create = new JButton("Add Staff");
        edit = new JButton("Update Staff");
        refresh = new JButton("Refresh Staff");

        p1a.add(create);
        create.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(edit);
        edit.addActionListener(this);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(refresh);
        refresh.addActionListener(this);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);


        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
        model = new DefaultTableModel(0, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return comp;
            }
        };
        model.setColumnIdentifiers(header);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);

        fillTable(staffList);

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        table.setColumnSelectionAllowed(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane sp = new JScrollPane();
        sp.setBackground(Color.WHITE);
        sp.setViewportView(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(500, 290));
        p2.add(sp);
        add(p2, BorderLayout.EAST);
    }

    public void fillTable(ArrayList<Staff> s) {
        System.out.println("Inside : fillTable() in StaffTabGUI");
        this.staffList = s;
        for (int i = 0; i < staffList.size(); i ++) {
            model.addRow(new Object[]{staffList.get(i).getId(), staffList.get(i).getlName(), staffList.get(i).getfName(),
                    staffList.get(i).getPhone()});
        }
    }

    public void refreshTable () {
        System.out.println("Inside : refreshTable() in StaffTabGUI");

        staffList.clear();
        Alley a = new Alley(progOps);
        staffList = a.getStaffList();

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        fillTable(staffList);
    }

    public String searchStaff() {
        System.out.println("Inside : searchStaff() in StaffTabGUI");
        String query = "";
        NumberValidator numValidator = new NumberValidator();
        staffId = new JTextField();
        staffName = new JTextField();
        Object[] options = {
                "Please Enter -\nStaff Id:", staffId,
                "Or\nStaff Name:", staffName
        };

        int option = JOptionPane.showConfirmDialog(null, options, "Search Staff", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (numValidator.isNumeric(staffId.getText())) {
                query = "staffId = " + staffId.getText();
            } else if (numValidator.isNumeric(staffName.getText()) == false && staffName.getText().contains(" ")) {
                String[] name = staffName.getText().split(" ");
                if (name.length < 2) {
                    throw new IllegalArgumentException("String not in correct format");
                } else {
                    query = "fName = '" + name[0] + "' AND lName = '" + name[1] + "'";
                }
            } else {
                throw new IllegalArgumentException("String " + staffName.getText() + " does not contain a ' ' (space)!");
            }
        }
        else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        return query;
    }

    public void updateStaff(String s) {
        System.out.println("Inside : updateStaff() in StaffTabGUI");
        UpdateStaffGUI us = new UpdateStaffGUI(this, progOps, s);
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Inside : actionPerformed() in StaffTabGUI");
        if (ae.getSource() == create) {
            AddStaffGUI as = new AddStaffGUI(this, progOps, staffList);
        }
        else if (ae.getSource() == edit) {
            String str = searchStaff();
            updateStaff(str);
        }
        else if (ae.getSource() == refresh) {
            refreshTable();
        }
    }
}