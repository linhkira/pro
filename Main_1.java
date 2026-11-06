import java.io.PrintStream;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * =====================================================================
 *  CHƯƠNG TRÌNH XỬ LÝ CHUỖI TRONG JAVA
 * =====================================================================
 *  Cách chạy:   javac Main.java
 *               java Main
 *  (Windows: nếu lỗi font tiếng Việt, gõ "chcp 65001" trước khi chạy)
 *
 *  Cách tổ chức:
 *   - Mỗi class lo MỘT nhóm công việc, toàn bộ là hàm static nên
 *     không cần "new", gọi thẳng: TênClass.tênHàm(...)
 *   - Các class KHÔNG nhập/xuất gì cả, chỉ nhận chuỗi vào và trả kết quả.
 *     Việc nhập từ bàn phím (Scanner) nằm hết trong hàm main ở cuối file.
 *   - Muốn đổi tên class / hàm / biến: dùng chức năng Rename của IDE
 *     (IntelliJ: Shift+F6, Eclipse: Alt+Shift+R, VS Code: F2),
 *     IDE sẽ tự sửa mọi chỗ đã gọi nên không sợ sót.
 *
 *  Danh sách class:
 *   1. StringBasic          - thao tác cơ bản (độ dài, đảo ngược, hoa/thường...)
 *   2. StringSearch         - tìm kiếm
 *   3. StringModify         - chỉnh sửa (thay thế, xóa, cắt, chèn...)
 *   4. StringCheck          - kiểm tra (palindrome, email, số điện thoại...)
 *   5. StringSplitJoin      - tách / ghép chuỗi, xử lý từ
 *   6. StringCompare        - so sánh chuỗi
 *   7. StringStatistics     - thống kê (đếm chữ, số, nguyên âm, tần suất...)
 *   8. StringConvert        - chuyển đổi (bỏ dấu, slug, camelCase, nhị phân...)
 *   9. StringBuilderHelper  - dùng StringBuilder
 *  10. StringFormatter      - căn lề, cắt ngắn, định dạng số
 *  11. RegexHelper          - biểu thức chính quy
 * =====================================================================
 */


// ---------------------------------------------------------------------
// 1. THAO TÁC CƠ BẢN
// ---------------------------------------------------------------------
class StringBasic {

    public static int length(String text) {
        return text.length();
    }

    public static boolean isEmpty(String text) {
        return text.isEmpty();
    }

    /** Rỗng hoặc chỉ toàn khoảng trắng. */
    public static boolean isBlank(String text) {
        return text.trim().isEmpty();
    }

    /** Lấy ký tự tại vị trí index (đếm từ 0). */
    public static char charAt(String text, int index) {
        if (index < 0 || index >= text.length()) {
            throw new IllegalArgumentException(
                    "Vị trí " + index + " nằm ngoài chuỗi (hợp lệ: 0 đến " + (text.length() - 1) + ")");
        }
        return text.charAt(index);
    }
    /** đổi từ số thành char
     char c = (char) asciiValue;
     */
    public static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    public static String toUpper(String text) {
        return text.toUpperCase();
    }

    public static String toLower(String text) {
        return text.toLowerCase();
    }

    /** Cắt khoảng trắng ở đầu và cuối chuỗi. */
    public static String trim(String text) {
        return text.trim();
    }

    /** "xin chào việt nam" -> "Xin Chào Việt Nam". */
    public static String capitalizeWords(String text) {
        StringBuilder result = new StringBuilder();
        boolean isStartOfWord = true;
        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                isStartOfWord = true;
                result.append(ch);
            } else if (isStartOfWord) {
                result.append(Character.toUpperCase(ch));
                isStartOfWord = false;
            } else {
                result.append(Character.toLowerCase(ch));
            }
        }
        return result.toString();
    }

    /** Đảo hoa <-> thường: "Hello" -> "hELLO". */
    public static String toggleCase(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(Character.toUpperCase(ch));
            }
        }
        return result.toString();
    }
}


// ---------------------------------------------------------------------
// 2. TÌM KIẾM
// ---------------------------------------------------------------------
class StringSearch {

    public static boolean contains(String text, String keyword) {
        return text.contains(keyword);
    }

    public static boolean containsIgnoreCase(String text, String keyword) {
        return text.toLowerCase().contains(keyword.toLowerCase());
    }

    /** Vị trí xuất hiện đầu tiên, -1 nếu không thấy. */
    public static int indexOf(String text, String keyword) {
        return text.indexOf(keyword);
    }

    /** Vị trí xuất hiện cuối cùng, -1 nếu không thấy. */
    public static int lastIndexOf(String text, String keyword) {
        return text.lastIndexOf(keyword);
    }

    /** Danh sách mọi vị trí xuất hiện (không tính chồng lấn). */
    public static List<Integer> findAllPositions(String text, String keyword) {
        List<Integer> positions = new ArrayList<>();
        if (keyword.isEmpty()) {
            return positions;
        }
        int index = text.indexOf(keyword);
        while (index != -1) {
            positions.add(index);
            index = text.indexOf(keyword, index + keyword.length());
        }
        return positions;
    }

    public static int countOccurrences(String text, String keyword) {
        return findAllPositions(text, keyword).size();
    }

    public static boolean startsWith(String text, String prefix) {
        return text.startsWith(prefix);
    }

    public static boolean endsWith(String text, String suffix) {
        return text.endsWith(suffix);
    }
}


// ---------------------------------------------------------------------
// 3. CHỈNH SỬA
// ---------------------------------------------------------------------
class StringModify {

    public static String replace(String text, String oldPart, String newPart) {
        return text.replace(oldPart, newPart);
    }

    public static String removeChar(String text, char ch) {
        return text.replace(String.valueOf(ch), "");
    }

    public static String removeAllSpaces(String text) {
        return text.replaceAll("\\s+", "");
    }

    /** Bỏ khoảng trắng 2 đầu và gộp nhiều khoảng trắng liền nhau thành 1. */
    public static String normalizeSpaces(String text) {
        return text.trim().replaceAll("\\s+", " ");
    }

    /** "banana" -> "ban" (giữ ký tự xuất hiện lần đầu, giữ nguyên thứ tự). */
    public static String removeDuplicateChars(String text) {
        StringBuilder result = new StringBuilder();
        Set<Character> seen = new HashSet<>();
        for (char ch : text.toCharArray()) {
            if (seen.add(ch)) {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /** Lấy đoạn từ vị trí start đến end (không gồm end). */
    public static String substring(String text, int start, int end) {
        checkRange(text, start, end);
        return text.substring(start, end);
    }

    /** Chèn chuỗi part vào vị trí index. */
    public static String insert(String text, int index, String part) {
        checkIndex(text, index);
        return text.substring(0, index) + part + text.substring(index);
    }

    /** Xóa đoạn từ vị trí start đến end (không gồm end). */
    public static String deleteRange(String text, int start, int end) {
        checkRange(text, start, end);
        return text.substring(0, start) + text.substring(end);
    }

    // ----- 2 hàm kiểm tra dùng chung (các class khác cũng gọi được) -----
    static void checkIndex(String text, int index) {
        if (index < 0 || index > text.length()) {
            throw new IllegalArgumentException(
                    "Vị trí " + index + " không hợp lệ (hợp lệ: 0 đến " + text.length() + ")");
        }
    }

    static void checkRange(String text, int start, int end) {
        if (start < 0 || end > text.length() || start > end) {
            throw new IllegalArgumentException(
                    "Đoạn [" + start + ", " + end + ") không hợp lệ với chuỗi dài " + text.length());
        }
    }
}


// ---------------------------------------------------------------------
// 4. KIỂM TRA
// ---------------------------------------------------------------------
class StringCheck {

    /** Chuỗi đối xứng (bỏ qua hoa/thường, khoảng trắng và dấu câu). */
    public static boolean isPalindrome(String text) {
        String cleaned = keepLettersAndDigits(text).toLowerCase();
        return new StringBuilder(cleaned).reverse().toString().equals(cleaned);
    }

    /** Hai chuỗi gồm các chữ cái giống nhau nhưng khác thứ tự ("listen" / "silent"). */
    public static boolean isAnagram(String first, String second) {
        char[] a = keepLettersAndDigits(first).toLowerCase().toCharArray();
        char[] b = keepLettersAndDigits(second).toLowerCase().toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    public static boolean isInteger(String text) {
        return text.matches("[+-]?\\d+");
    }

    public static boolean isDecimal(String text) {
        return text.matches("[+-]?\\d+(\\.\\d+)?");
    }

    /** Toàn là chữ cái (kể cả chữ có dấu tiếng Việt). */
    public static boolean isOnlyLetters(String text) {
        if (text.isEmpty()) {
            return false;
        }
        for (char ch : text.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String text) {
        return text.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    }

    /** Số điện thoại Việt Nam: 0xxxxxxxxx hoặc +84xxxxxxxxx. */
    public static boolean isPhoneVN(String text) {
        return text.matches("^(0|\\+84)\\d{9}$");
    }

    private static String keepLettersAndDigits(String text) {
        return text.replaceAll("[^\\p{L}\\p{N}]", "");
    }
}


// ---------------------------------------------------------------------
// 5. TÁCH / GHÉP CHUỖI
// ---------------------------------------------------------------------
class StringSplitJoin {

    /** Tách thành các từ (tự bỏ khoảng trắng thừa). */
    public static String[] splitWords(String text) {
        String cleaned = text.trim();
        if (cleaned.isEmpty()) {
            return new String[0];
        }
        return cleaned.split("\\s+");
    }

    public static int countWords(String text) {
        return splitWords(text).length;
    }

    /** Tách theo một chuỗi phân cách bất kỳ (ví dụ ",", ";", "|"). */
    public static String[] splitBy(String text, String delimiter) {
        return text.split(Pattern.quote(delimiter));
    }

    public static String join(String[] parts, String delimiter) {
        return String.join(delimiter, parts);
    }

    /** Sắp xếp các từ theo thứ tự A-Z (không phân biệt hoa/thường). */
    public static String sortWords(String text) {
        String[] words = splitWords(text);
        Arrays.sort(words, String.CASE_INSENSITIVE_ORDER);
        return join(words, " ");
    }

    /** "một hai ba" -> "ba hai một". */
    public static String reverseWordOrder(String text) {
        String[] words = splitWords(text);
        Collections.reverse(Arrays.asList(words));
        return join(words, " ");
    }

    /** Tách bằng StringTokenizer (cách cũ), delimiters là TẬP các ký tự phân cách. */
    public static List<String> splitWithTokenizer(String text, String delimiters) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text, delimiters);
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }
}


// ---------------------------------------------------------------------
// 6. SO SÁNH
// ---------------------------------------------------------------------
class StringCompare {

    public static boolean areEqual(String a, String b) {
        return a.equals(b);
    }

    public static boolean areEqualIgnoreCase(String a, String b) {
        return a.equalsIgnoreCase(b);
    }

    /** 0: bằng nhau, âm: a đứng trước b, dương: a đứng sau b. */
    public static int compare(String a, String b) {
        return a.compareTo(b);
    }

    public static int compareIgnoreCase(String a, String b) {
        return a.compareToIgnoreCase(b);
    }

    /** Mô tả kết quả so sánh bằng lời. */
    public static String describe(String a, String b) {
        int result = compare(a, b);
        if (result == 0) {
            return "Hai chuỗi giống hệt nhau";
        } else if (result < 0) {
            return "\"" + a + "\" đứng TRƯỚC \"" + b + "\" (theo thứ tự bảng mã)";
        } else {
            return "\"" + a + "\" đứng SAU \"" + b + "\" (theo thứ tự bảng mã)";
        }
    }
}


// ---------------------------------------------------------------------
// 7. THỐNG KÊ
// ---------------------------------------------------------------------
class StringStatistics {

    public static int countLetters(String text) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) count++;
        }
        return count;
    }

    public static int countDigits(String text) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (Character.isDigit(ch)) count++;
        }
        return count;
    }

    public static int countSpaces(String text) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) count++;
        }
        return count;
    }

    /** Ký tự đặc biệt = không phải chữ, không phải số, không phải khoảng trắng. */
    public static int countSpecialChars(String text) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)) count++;
        }
        return count;
    }

    /** Đếm nguyên âm (a, e, i, o, u); chữ có dấu được quy về chữ gốc trước khi đếm. */
    public static int countVowels(String text) {
        String plain = StringConvert.removeAccents(text).toLowerCase();
        int count = 0;
        for (char ch : plain.toCharArray()) {
            if ("aeiou".indexOf(ch) != -1) count++;
        }
        return count;
    }

    public static int countConsonants(String text) {
        String plain = StringConvert.removeAccents(text).toLowerCase();
        int count = 0;
        for (char ch : plain.toCharArray()) {
            if (Character.isLetter(ch) && "aeiou".indexOf(ch) == -1) count++;
        }
        return count;
    }

    /** Tần suất xuất hiện của từng ký tự (bỏ qua khoảng trắng, phân biệt hoa/thường). */
    public static Map<Character, Integer> charFrequency(String text) {
        Map<Character, Integer> frequency = new LinkedHashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) continue;
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }
        return frequency;
    }

    /** Ký tự xuất hiện nhiều nhất, trả về null nếu chuỗi không có ký tự nào. */
    public static Character mostFrequentChar(String text) {
        Character best = null;
        int bestCount = 0;
        for (Map.Entry<Character, Integer> entry : charFrequency(text).entrySet()) {
            if (entry.getValue() > bestCount) {
                best = entry.getKey();
                bestCount = entry.getValue();
            }
        }
        return best;
    }

    public static String longestWord(String text) {
        String best = "";
        for (String word : StringSplitJoin.splitWords(text)) {
            if (word.length() > best.length()) best = word;
        }
        return best;
    }

    public static String shortestWord(String text) {
        String[] words = StringSplitJoin.splitWords(text);
        if (words.length == 0) return "";
        String best = words[0];
        for (String word : words) {
            if (word.length() < best.length()) best = word;
        }
        return best;
    }
}


// ---------------------------------------------------------------------
// 8. CHUYỂN ĐỔI
// ---------------------------------------------------------------------
class StringConvert {

    public static char[] toCharArray(String text) {
        return text.toCharArray();
    }

    public static int toInt(String text) {
        return Integer.parseInt(text.trim());
    }

    public static double toDouble(String text) {
        return Double.parseDouble(text.trim());
    }

    public static String intToBinary(int number) {
        return Integer.toBinaryString(number);
    }

    public static String intToOctal(int number) {
        return Integer.toOctalString(number);
    }

    public static String intToHex(int number) {
        return Integer.toHexString(number).toUpperCase();
    }

    /** "1010" -> 10. */
    public static int binaryToInt(String binary) {
        return Integer.parseInt(binary.trim(), 2);
    }

    /** "Hi" -> "72 105". */
    public static String toAsciiCodes(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            result.append((int) ch).append(' ');
        }
        return result.toString().trim();
    }

    /** "Việt Nam đẹp" -> "Viet Nam dep". */
    public static String removeAccents(String text) {
        String decomposed = Normalizer.normalize(text, Normalizer.Form.NFD);
        return decomposed
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace('đ', 'd')
                .replace('Đ', 'D');
    }

    /** "Xin chào Việt Nam!" -> "xin-chao-viet-nam" (dùng cho đường dẫn URL). */
    public static String toSlug(String text) {
        String slug = removeAccents(text).toLowerCase().trim();
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");
        slug = slug.replaceAll("[\\s-]+", "-");
        return slug.replaceAll("^-|-$", "");
    }

    /** "hello big world" -> "helloBigWorld". */
    public static String toCamelCase(String text) {
        String[] words = StringSplitJoin.splitWords(text);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (i == 0) {
                result.append(word);
            } else {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
        }
        return result.toString();
    }

    /** "Hello Big World" -> "hello_big_world". */
    public static String toSnakeCase(String text) {
        String[] words = StringSplitJoin.splitWords(text);
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }
        return String.join("_", words);
    }
}


// ---------------------------------------------------------------------
// 9. STRINGBUILDER (nhanh hơn String khi nối / sửa chuỗi nhiều lần)
// ---------------------------------------------------------------------
class StringBuilderHelper {

    public static String append(String text, String part) {
        return new StringBuilder(text).append(part).toString();
    }

    public static String insert(String text, int index, String part) {
        StringModify.checkIndex(text, index);
        return new StringBuilder(text).insert(index, part).toString();
    }

    public static String deleteRange(String text, int start, int end) {
        StringModify.checkRange(text, start, end);
        return new StringBuilder(text).delete(start, end).toString();
    }

    /** Thay đoạn [start, end) bằng chuỗi part. */
    public static String replaceRange(String text, int start, int end, String part) {
        StringModify.checkRange(text, start, end);
        return new StringBuilder(text).replace(start, end, part).toString();
    }

    public static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}


// ---------------------------------------------------------------------
// 10. ĐỊNH DẠNG
// ---------------------------------------------------------------------
class StringFormatter {

    /** Thêm ký tự fill vào bên trái cho đủ độ rộng width. */
    public static String padLeft(String text, int width, char fill) {
        return repeatChar(fill, width - text.length()) + text;
    }

    public static String padRight(String text, int width, char fill) {
        return text + repeatChar(fill, width - text.length());
    }

    public static String center(String text, int width, char fill) {
        int totalFill = width - text.length();
        if (totalFill <= 0) return text;
        int left = totalFill / 2;
        int right = totalFill - left;
        return repeatChar(fill, left) + text + repeatChar(fill, right);
    }

    /** Cắt ngắn còn tối đa maxLength ký tự, thêm "..." nếu bị cắt. */
    public static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        if (maxLength <= 3) return text.substring(0, Math.max(0, maxLength));
        return text.substring(0, maxLength - 3) + "...";
    }

    /** 1234567 -> "1.234.567" (kiểu Việt Nam). */
    public static String formatNumber(long number) {
        return String.format(Locale.US, "%,d", number).replace(',', '.');
    }

    private static String repeatChar(char ch, int times) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < times; i++) {
            result.append(ch);
        }
        return result.toString();
    }
}


// ---------------------------------------------------------------------
// 11. BIỂU THỨC CHÍNH QUY (REGEX)
// ---------------------------------------------------------------------
class RegexHelper {

    /** Toàn bộ chuỗi có khớp mẫu regex không. */
    public static boolean matches(String text, String regex) {
        return text.matches(regex);
    }

    /** Tìm mọi đoạn khớp mẫu regex. */
    public static List<String> findAll(String text, String regex) {
        List<String> results = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(text);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }

    public static String replaceAll(String text, String regex, String replacement) {
        return text.replaceAll(regex, replacement);
    }

    public static List<String> extractNumbers(String text) {
        return findAll(text, "\\d+(\\.\\d+)?");
    }

    public static List<String> extractEmails(String text) {
        return findAll(text, "[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+");
    }
}


// =====================================================================
//  HÀM MAIN - toàn bộ việc nhập từ bàn phím nằm ở đây
// =====================================================================
public class Main_1 {

    public static void main(String[] args) throws Exception {
        // Để in/nhập được tiếng Việt có dấu
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Scanner sc = new Scanner(System.in, "UTF-8");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Chọn chức năng: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {

                    // ----- 1. CƠ BẢN -----
                    case "1": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.println("Độ dài                : " + StringBasic.length(text));
                        System.out.println("Chuỗi rỗng?           : " + StringBasic.isEmpty(text));
                        System.out.println("Rỗng/toàn khoảng trắng: " + StringBasic.isBlank(text));
                        System.out.println("Cắt khoảng trắng 2 đầu: [" + StringBasic.trim(text) + "]");
                        System.out.println("Đảo ngược             : " + StringBasic.reverse(text));
                        System.out.println("IN HOA                : " + StringBasic.toUpper(text));
                        System.out.println("in thường             : " + StringBasic.toLower(text));
                        System.out.println("Viết Hoa Đầu Mỗi Từ   : " + StringBasic.capitalizeWords(text));
                        System.out.println("Đảo hoa/thường        : " + StringBasic.toggleCase(text));
                        System.out.print("Nhập vị trí ký tự muốn lấy (đếm từ 0): ");
                        int index = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Ký tự tại vị trí " + index + " : " + StringBasic.charAt(text, index));
                        break;
                    }

                    // ----- 2. TÌM KIẾM -----
                    case "2": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.print("Nhập từ khóa cần tìm: ");
                        String keyword = sc.nextLine();
                        System.out.println("Có chứa?                 : " + StringSearch.contains(text, keyword));
                        System.out.println("Có chứa (bỏ qua hoa/thg) : " + StringSearch.containsIgnoreCase(text, keyword));
                        System.out.println("Vị trí đầu tiên          : " + StringSearch.indexOf(text, keyword));
                        System.out.println("Vị trí cuối cùng         : " + StringSearch.lastIndexOf(text, keyword));
                        System.out.println("Tất cả vị trí            : " + StringSearch.findAllPositions(text, keyword));
                        System.out.println("Số lần xuất hiện         : " + StringSearch.countOccurrences(text, keyword));
                        System.out.println("Bắt đầu bằng từ khóa?    : " + StringSearch.startsWith(text, keyword));
                        System.out.println("Kết thúc bằng từ khóa?   : " + StringSearch.endsWith(text, keyword));
                        break;
                    }

                    // ----- 3. CHỈNH SỬA -----
                    case "3": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.println("Bỏ hết khoảng trắng     : " + StringModify.removeAllSpaces(text));
                        System.out.println("Chuẩn hóa khoảng trắng  : " + StringModify.normalizeSpaces(text));
                        System.out.println("Bỏ ký tự trùng lặp      : " + StringModify.removeDuplicateChars(text));

                        System.out.print("Nhập đoạn cần thay thế: ");
                        String oldPart = sc.nextLine();
                        System.out.print("Nhập đoạn thay vào: ");
                        String newPart = sc.nextLine();
                        System.out.println("Sau khi thay thế        : " + StringModify.replace(text, oldPart, newPart));

                        System.out.print("Nhập ký tự cần xóa khỏi chuỗi: ");
                        String charText = sc.nextLine();
                        if (!charText.isEmpty()) {
                            System.out.println("Sau khi xóa ký tự       : " + StringModify.removeChar(text, charText.charAt(0)));
                        }

                        System.out.print("Nhập vị trí bắt đầu (start): ");
                        int start = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Nhập vị trí kết thúc (end, không tính end): ");
                        int end = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Đoạn cắt được           : " + StringModify.substring(text, start, end));
                        System.out.println("Sau khi xóa đoạn đó     : " + StringModify.deleteRange(text, start, end));

                        System.out.print("Nhập vị trí cần chèn: ");
                        int insertIndex = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Nhập chuỗi cần chèn: ");
                        String insertPart = sc.nextLine();
                        System.out.println("Sau khi chèn            : " + StringModify.insert(text, insertIndex, insertPart));
                        break;
                    }

                    // ----- 4. KIỂM TRA -----
                    case "4": {
                        System.out.print("Nhập chuỗi cần kiểm tra: ");
                        String text = sc.nextLine();
                        System.out.println("Đối xứng (palindrome)?  : " + StringCheck.isPalindrome(text));
                        System.out.println("Là số nguyên?           : " + StringCheck.isInteger(text));
                        System.out.println("Là số thực?             : " + StringCheck.isDecimal(text));
                        System.out.println("Chỉ gồm chữ cái?        : " + StringCheck.isOnlyLetters(text));
                        System.out.println("Là email hợp lệ?        : " + StringCheck.isEmail(text));
                        System.out.println("Là SĐT Việt Nam?        : " + StringCheck.isPhoneVN(text));
                        System.out.print("Nhập chuỗi thứ 2 để kiểm tra đảo chữ (anagram): ");
                        String other = sc.nextLine();
                        System.out.println("Là anagram của nhau?    : " + StringCheck.isAnagram(text, other));
                        break;
                    }

                    // ----- 5. TÁCH / GHÉP -----
                    case "5": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.println("Các từ                  : " + Arrays.toString(StringSplitJoin.splitWords(text)));
                        System.out.println("Số từ                   : " + StringSplitJoin.countWords(text));
                        System.out.println("Sắp xếp các từ A-Z      : " + StringSplitJoin.sortWords(text));
                        System.out.println("Đảo thứ tự các từ       : " + StringSplitJoin.reverseWordOrder(text));
                        System.out.print("Nhập ký tự/chuỗi phân cách để tách (vd: , hoặc ;): ");
                        String delimiter = sc.nextLine();
                        String[] parts = StringSplitJoin.splitBy(text, delimiter);
                        System.out.println("Sau khi tách            : " + Arrays.toString(parts));
                        System.out.println("Ghép lại bằng \" - \"     : " + StringSplitJoin.join(parts, " - "));
                        System.out.println("Tách bằng Tokenizer     : " + StringSplitJoin.splitWithTokenizer(text, delimiter));
                        break;
                    }

                    // ----- 6. SO SÁNH -----
                    case "6": {
                        System.out.print("Nhập chuỗi thứ nhất: ");
                        String a = sc.nextLine();
                        System.out.print("Nhập chuỗi thứ hai: ");
                        String b = sc.nextLine();
                        System.out.println("Bằng nhau?                  : " + StringCompare.areEqual(a, b));
                        System.out.println("Bằng nhau (bỏ qua hoa/thg)? : " + StringCompare.areEqualIgnoreCase(a, b));
                        System.out.println("Kết quả compare             : " + StringCompare.compare(a, b));
                        System.out.println("Kết quả compare (bỏ hoa/thg): " + StringCompare.compareIgnoreCase(a, b));
                        System.out.println("Diễn giải                   : " + StringCompare.describe(a, b));
                        break;
                    }

                    // ----- 7. THỐNG KÊ -----
                    case "7": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        Character most = StringStatistics.mostFrequentChar(text);
                        System.out.println("Số chữ cái              : " + StringStatistics.countLetters(text));
                        System.out.println("Số chữ số               : " + StringStatistics.countDigits(text));
                        System.out.println("Số khoảng trắng         : " + StringStatistics.countSpaces(text));
                        System.out.println("Số ký tự đặc biệt       : " + StringStatistics.countSpecialChars(text));
                        System.out.println("Số nguyên âm            : " + StringStatistics.countVowels(text));
                        System.out.println("Số phụ âm               : " + StringStatistics.countConsonants(text));
                        System.out.println("Từ dài nhất             : " + StringStatistics.longestWord(text));
                        System.out.println("Từ ngắn nhất            : " + StringStatistics.shortestWord(text));
                        System.out.println("Ký tự xuất hiện nhiều nhất: " + (most == null ? "(không có)" : most));
                        System.out.println("Tần suất từng ký tự     : " + StringStatistics.charFrequency(text));
                        break;
                    }

                    // ----- 8. CHUYỂN ĐỔI -----
                    case "8": {
                        System.out.print("Nhập chuỗi (có thể có dấu tiếng Việt): ");
                        String text = sc.nextLine();
                        System.out.println("Bỏ dấu                  : " + StringConvert.removeAccents(text));
                        System.out.println("Slug (dùng cho URL)     : " + StringConvert.toSlug(text));
                        System.out.println("camelCase               : " + StringConvert.toCamelCase(StringConvert.removeAccents(text)));
                        System.out.println("snake_case              : " + StringConvert.toSnakeCase(StringConvert.removeAccents(text)));
                        System.out.println("Mã Unicode từng ký tự   : " + StringConvert.toAsciiCodes(text));
                        System.out.println("Mảng ký tự              : " + Arrays.toString(StringConvert.toCharArray(text)));

                        System.out.print("Nhập một số nguyên (dạng chuỗi): ");
                        int number = StringConvert.toInt(sc.nextLine());
                        System.out.println("Nhị phân                : " + StringConvert.intToBinary(number));
                        System.out.println("Bát phân                : " + StringConvert.intToOctal(number));
                        System.out.println("Thập lục phân (hex)     : " + StringConvert.intToHex(number));

                        System.out.print("Nhập một số thực (vd 3.14): ");
                        double decimal = StringConvert.toDouble(sc.nextLine());
                        System.out.println("Số thực nhân đôi        : " + (decimal * 2));

                        System.out.print("Nhập chuỗi nhị phân (vd 1010): ");
                        System.out.println("Giá trị thập phân       : " + StringConvert.binaryToInt(sc.nextLine()));
                        break;
                    }

                    // ----- 9. STRINGBUILDER -----
                    case "9": {
                        System.out.print("Nhập chuỗi gốc: ");
                        String text = sc.nextLine();
                        System.out.print("Nhập chuỗi phụ (để nối / chèn / thay thế): ");
                        String part = sc.nextLine();
                        System.out.print("Nhập vị trí bắt đầu (start): ");
                        int start = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Nhập vị trí kết thúc (end): ");
                        int end = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Nối thêm vào cuối       : " + StringBuilderHelper.append(text, part));
                        System.out.println("Chèn tại vị trí start   : " + StringBuilderHelper.insert(text, start, part));
                        System.out.println("Xóa đoạn [start, end)   : " + StringBuilderHelper.deleteRange(text, start, end));
                        System.out.println("Thay đoạn [start, end)  : " + StringBuilderHelper.replaceRange(text, start, end, part));
                        System.out.println("Đảo ngược               : " + StringBuilderHelper.reverse(text));
                        break;
                    }

                    // ----- 10. ĐỊNH DẠNG -----
                    case "10": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.print("Nhập độ rộng (width): ");
                        int width = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Căn phải  : [" + StringFormatter.padLeft(text, width, '*') + "]");
                        System.out.println("Căn trái  : [" + StringFormatter.padRight(text, width, '*') + "]");
                        System.out.println("Căn giữa  : [" + StringFormatter.center(text, width, '*') + "]");
                        System.out.println("Cắt ngắn  : [" + StringFormatter.truncate(text, width) + "]");
                        System.out.print("Nhập một số nguyên lớn (vd 1234567): ");
                        long bigNumber = Long.parseLong(sc.nextLine().trim());
                        System.out.println("Định dạng số: " + StringFormatter.formatNumber(bigNumber));
                        break;
                    }

                    // ----- 11. REGEX -----
                    case "11": {
                        System.out.print("Nhập chuỗi: ");
                        String text = sc.nextLine();
                        System.out.print("Nhập biểu thức chính quy (vd \\d+ để tìm số): ");
                        String regex = sc.nextLine();
                        System.out.println("Khớp toàn bộ chuỗi?     : " + RegexHelper.matches(text, regex));
                        System.out.println("Các đoạn tìm thấy       : " + RegexHelper.findAll(text, regex));
                        System.out.print("Nhập chuỗi thay thế: ");
                        String replacement = sc.nextLine();
                        System.out.println("Sau khi thay thế        : " + RegexHelper.replaceAll(text, regex, replacement));
                        System.out.println("Các số trong chuỗi      : " + RegexHelper.extractNumbers(text));
                        System.out.println("Các email trong chuỗi   : " + RegexHelper.extractEmails(text));
                        break;
                    }

                    case "0":
                        running = false;
                        System.out.println("Tạm biệt!");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ, hãy nhập số từ 0 đến 11.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: bạn cần nhập một con số hợp lệ.");
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== MENU XỬ LÝ CHUỖI ==========");
        System.out.println(" 1. Cơ bản            (StringBasic)");
        System.out.println(" 2. Tìm kiếm          (StringSearch)");
        System.out.println(" 3. Chỉnh sửa         (StringModify)");
        System.out.println(" 4. Kiểm tra          (StringCheck)");
        System.out.println(" 5. Tách / Ghép       (StringSplitJoin)");
        System.out.println(" 6. So sánh           (StringCompare)");
        System.out.println(" 7. Thống kê          (StringStatistics)");
        System.out.println(" 8. Chuyển đổi        (StringConvert)");
        System.out.println(" 9. StringBuilder     (StringBuilderHelper)");
        System.out.println("10. Định dạng         (StringFormatter)");
        System.out.println("11. Regex             (RegexHelper)");
        System.out.println(" 0. Thoát");
    }
}
