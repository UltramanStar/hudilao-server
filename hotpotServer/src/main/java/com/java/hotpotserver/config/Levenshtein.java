package com.java.hotpotserver.config;

import java.util.HashMap;
import java.util.Map;
//写一个算法，用于计算两个字符串的相似程度
public class Levenshtein {
    private int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1

        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        d = new int[n + 1][m + 1];

        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }

        return d[n][m];
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     *
     * @param str
     * @param target
     *
     * @return
     */

    public float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }
    //实现算法
    public static String getKeyWord(String keyWord, String userAnswer, Integer index) {
        Levenshtein levenshtein = new Levenshtein();
        String[] keyWordArr = keyWord.split("");
        String[] answerArr = userAnswer.split("");
        String begin = keyWordArr[index];
        int beginIndex = 0;
        //当前记录数
        int pitchIndex = 0;
        //命中暂时记录数
        int pitchAnswerIndex = 0;
        //间隔数
        int intervalIndex = 0;
        float percentage = 0;
        String result = "";
        String result2 = "";
        index++;
        Map<String, Integer> map = new HashMap<>();
        //设置字符可消费的数量
        for (int i = 0; i < keyWordArr.length; i++) {
            Integer num = map.get(keyWordArr[i]);
            if (null != num) {
                map.put(keyWordArr[i], num + 1);
            } else {
                map.put(keyWordArr[i], 1);
            }
        }
        for (int j = 0; j < answerArr.length; j++) {
            String answer = answerArr[j];
            if (begin.equals(answer)) {
                if (result.equals("")) {
                    result = answer;
                } else if (intervalIndex >= keyWord.length() * 0.2) {
                    result = answer;
                } else {
                    result = result + answer;
                }
                beginIndex = 1;
                pitchIndex++;
            } else {
                if (beginIndex == 1) {
                    //命中间隔判断，超过原词汇0.2未命中则视为失效，重新查找
                    if (intervalIndex >= keyWord.length() * 0.2) {
                        beginIndex = 0;
                        result = "";
                        result2 = "";
                        pitchIndex = 0;
                        pitchAnswerIndex = 0;
                        intervalIndex = 0;
                        continue;
                    }
                    //消费逻辑
                    Integer num = map.get(answer);
                    if (null != num && num > 0) {
                        map.put(answer, num - 1);
                        pitchIndex++;
                    } else {
                        intervalIndex++;
                    }
                    result = result + answer;
                    float percentage2 = levenshtein.getSimilarityRatio(result, keyWord);
                    //获取暂时满足条件的串
                    if (percentage2 >= percentage || pitchIndex > pitchAnswerIndex) {
                        result2 = result;
                        percentage = levenshtein.getSimilarityRatio(result2, keyWord);
                        pitchAnswerIndex = pitchIndex;
                        intervalIndex = 0;
                    }
                    if (intervalIndex >= keyWord.length() * 0.2 && pitchAnswerIndex > keyWord.length() * 0.6) {
                        break;
                    }
                }
            }
        }
        //返回满足百分之60以上的最优结果
        if (pitchAnswerIndex >= keyWord.length() * 0.6) {
            return result2;
        }
        //如不满足则重新查找
        if (index < keyWord.length()) {
            return getKeyWord(keyWord, userAnswer, index);
        } else {
            return null;
        }
    }
    public static void main(String[] args) {
        String a = "我要糖葫芦";
        String b = "糖葫卢";
        String c = getKeyWord(b,a,0);
        System.out.println("取词结果为:" + c);
    }
}
