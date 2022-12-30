package tetris2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class LeaderboardForm extends javax.swing.JFrame {

    private DefaultTableModel dtm;
    private String leaderboardFile = "leaderboard";
    private TableRowSorter<TableModel> rowSorter;

    public LeaderboardForm() {
        initComponents();
        initTable();
        initTableSorter();
    }
    
    private void initTable()
    {
        dtm = ( DefaultTableModel ) leaderboard.getModel();
        
        Vector columns = new Vector();
        columns.add("Name");
        columns.add("Level");
        columns.add("Score");
        
        try
        {
            FileInputStream fs = new FileInputStream( leaderboardFile );
            ObjectInputStream os = new ObjectInputStream( fs );
            dtm.setDataVector((Vector)os.readObject(), columns);
            os.close();
            fs.close();
        }   
        catch( Exception e)
        {
            System.out.println("Error loading leaderboard");
        }
    }
    
    private void initTableSorter()
    {
        this.rowSorter = new TableRowSorter<>( dtm );
        leaderboard.setRowSorter(rowSorter);
        ArrayList<SortKey> keys = new ArrayList<>();
        keys.add( new SortKey( 1, SortOrder.DESCENDING ) );
        keys.add( new SortKey( 2, SortOrder.DESCENDING ) );
        rowSorter.setSortKeys(keys);
    }
    
    private void saveLeaderboard()
    {
        try
        {
            FileOutputStream fs = new FileOutputStream( leaderboardFile );
            ObjectOutputStream os = new ObjectOutputStream( fs );
            os.writeObject( dtm.getDataVector() );
            os.close();
            fs.close();
        }
        catch(Exception e)
        {
            System.out.println("Error saving leaderboard");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        leaderboard = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        menuButton.setText("Back to Main Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        leaderboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Level", "Score"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        leaderboard.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(leaderboard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuButton))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(menuButton)
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        
        this.setVisible( false );
        Tetris2.showMenu();
        
    }//GEN-LAST:event_menuButtonActionPerformed

    public void addPlayer( String name, int lvl, int score )
    {
        dtm.addRow( new Object[] { name, lvl, score } );
        saveLeaderboard();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LeaderboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeaderboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeaderboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeaderboardForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LeaderboardForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable leaderboard;
    private javax.swing.JButton menuButton;
    // End of variables declaration//GEN-END:variables
}
