package movieproject.payment;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

 

public class JTextFieldLimit extends PlainDocument {
   private int limit;
   private boolean toUppercase = false;
   private String newValue;

 

   JTextFieldLimit(int limit) {
      super();
      this.limit = limit;
   }

 

   JTextFieldLimit(int limit, boolean upper) {
      super();
      this.limit = limit;
      this.toUppercase = upper;
   }

 

   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (str == null) {
         return;
      }

      if ( (getLength() + str.length()) <= limit) {
         if (toUppercase) {
            str = str.toUpperCase();
         }
         super.insertString(offset, str, attr);
      }
   }
   
//   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
//       if (str == null) {
//               return;
//           }
//       else {
//           String newValue;
//           int length = getLength();
//           if (length == 0) {
//               newValue = str;
//           } else {
//               String currentContent = getText(0, length);
//               StringBuffer currentBuffer = new StringBuffer(currentContent);
//               currentBuffer.insert(offset, str);
//               newValue = currentBuffer.toString();
//           }
//          
//           try {
//               Integer.parseInt(newValue);
//               super.insertString(offset, str, attr);
//           } catch (NumberFormatException exception) {
//               //System.out.println("숫자를 입력하세요");
//               JOptionPane.showMessageDialog(null, "숫자를 입력하세요");
//           }
//       }
//   }
}