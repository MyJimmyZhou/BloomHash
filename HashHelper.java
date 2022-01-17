import javax.print.attribute.HashPrintServiceAttributeSet;

/**
 * 9个常用hash计算函数Java语言实现
 * C语言实现地址：http://www.partow.net/programming/hashfunctions/index.html
 */
public class HashHelper {
    private static long maxUInt = 4294967296L;

    /**
     * Java没有无符号类型，所以无法表达C语言中的unsigned int
     * 但我们可以用long表达更大的数值
     * 然后，我们把计算出来的值给转换成源代码中溢出的值
     *
     * @param str
     * @return
     */
    public static long RSHash(String str) {
        int length = str.length();
        long b = 378551;
        long a = 63689;
        long hash = 0;
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            int ascii = Integer.valueOf(arrStr[i]);
            hash = hash * a + ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
            a = a * b;
            if (a > maxUInt) {
                a = a % maxUInt;
            }
        }
        return hash;
    }

    public static long JSHash(String str) {
        int length = str.length();
        long hash = 1315423911L;
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            int ascii = Integer.valueOf(arrStr[i]);
            long temp1 = hash << 5;
            if (temp1 > maxUInt) {
                temp1 = temp1 % maxUInt;
            }
            long temp2 = hash >> 2;
            if (temp2 > maxUInt) {
                temp2 = temp1 % maxUInt;
            }
            hash = hash ^ (temp1 + ascii + temp2);
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }

    public static long PJWHash(String str) {
        // 源代码这个值是int的字节数*8。在语言中，int类型在32位系统中是2位，在64位系统中是4位，目前基本都是64位的系统啦。
        long bitsInUnsignedInt = 4 * 8;
        long threesQuarters = (bitsInUnsignedInt * 3) / 4;
        long oneEighth = bitsInUnsignedInt / 8;
        long highBits = (0xFFFFFFFF) << (bitsInUnsignedInt - oneEighth);
        long hash = 0;
        long test = 0;
        int length = str.length();
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            int ascii = Integer.valueOf(arrStr[i]);
            hash = (hash << oneEighth) + ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
            if ((test = hash & highBits) != 0) {
                long temp2 = test >> threesQuarters;
                if (temp2 > maxUInt) {
                    temp2 = temp2 % maxUInt;
                }
                long temp3 = hash ^ temp2;
                if (temp3 > maxUInt) {
                    temp3 = temp3 % maxUInt;
                }
                hash = temp3 & (~highBits);
                if (hash > maxUInt) {
                    hash = hash % maxUInt;
                }
                System.out.println("hash:" + hash);
            }
        }
        return hash;
    }

    public static long ELFHash(String str) {
        long hash = 0;
        long x = 0;
        int length = str.length();
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            int ascii = Integer.valueOf(arrStr[i]);
            hash = hash << 4;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
            hash = hash + ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
            x = hash & 0xF000000L;
            if (x > maxUInt) {
                x = x % maxUInt;
            }
            if (x != 0) {
                hash ^= (x >> 24);
                if (hash > maxUInt) {
                    hash = hash % maxUInt;
                }
            }
            hash &= ~x;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }


    public static long BKDRHash(String str) {
        // 种子数可以选择： 31 131 1313 13131 131313 etc..
        long seed = 131;
        long hash = 0;
        char[] arrStr = str.toCharArray();
        int length = str.length();
        for (int i = 0; i < length; ++i) {
            hash = hash * seed;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
            int ascii = Integer.valueOf(arrStr[i]);
            hash = hash + ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }

    public static long SDBMHash(String str) {
        long hash = 0;
        int length = str.length();
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            long temp1 = hash << 6;
            if (temp1 > maxUInt) {
                temp1 = temp1 % maxUInt;
            }
            long temp2 = hash << 16;
            if (temp2 > maxUInt) {
                temp2 = temp2 % maxUInt;
            }
            int ascii = Integer.valueOf(arrStr[i]);
            hash = ascii + temp1 + temp2 - hash;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }

    public static long DJBHash(String str) {
        long hash = 5381;
        int length = str.length();
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            long temp1 = hash << 5;
            if (temp1 > maxUInt) {
                temp1 = temp1 % maxUInt;
            }
            int ascii = Integer.valueOf(arrStr[i]);
            hash = (temp1 + hash) + ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }

    public static long DEKHash(String str) {
        int length = str.length();
        long hash = length;
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            long temp1 = hash << 5;
            if (temp1 > maxUInt) {
                temp1 = temp1 % maxUInt;
            }
            long temp2 = hash >> 27;
            if (temp2 > maxUInt) {
                temp2 = temp2 % maxUInt;
            }
            int ascii = Integer.valueOf(arrStr[i]);
            hash = temp1 ^ temp2 ^ ascii;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }

    public static long APHash(String str) {
        long hash = 0xAAAAAAAAL;
        int length = str.length();
        char[] arrStr = str.toCharArray();
        for (int i = 0; i < length; ++i) {
            long temp1 = hash << 7;
            if (temp1 > maxUInt) {
                temp1 = temp1 % maxUInt;
            }
            long temp2 = hash >> 3;
            if (temp2 > maxUInt) {
                temp2 = temp2 % maxUInt;
            }
            long temp3 = hash << 11;
            if (temp3 > maxUInt) {
                temp3 = temp3 % maxUInt;
            }
            long temp4 = hash >> 5;
            if (temp4 > maxUInt) {
                temp4 = temp4 % maxUInt;
            }
            int ascii = Integer.valueOf(arrStr[i]);
            long temp5 = ~(temp3 + (ascii ^ temp4));
            if (temp5 > maxUInt) {
                temp5 = temp5 % maxUInt;
            } else if (temp5 < 0) {
                temp5 = maxUInt + temp5;
            }
            hash ^= ((i & 1) == 0) ? (temp1 ^ ascii * temp2) : temp5;
            if (hash > maxUInt) {
                hash = hash % maxUInt;
            }
        }
        return hash;
    }
}
