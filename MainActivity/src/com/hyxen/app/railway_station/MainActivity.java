package com.hyxen.app.railway_station;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends Activity //implements SearchView.OnQueryTextListener, View.OnClickListener
{

    /**
     * %1 String date:2014/02/08
     * %2 String station:1008
     */
    private static final String URL_FORMAT = "http://twtraffic.tra.gov.tw/twrail/mobile/StationSearchResult.aspx?searchdate=2014/02/13&fromstation=1008";
    private static final String AREA_JSON = "[{\"id\":\"0\",\"cname\":\"\\u81fa\\u5317\\u5730\\u5340\",\"ename\":\"Taipei\",\"stn\":[\"\\u798f\\u9686\",\"\\u8ca2\\u5bee\",\"\\u96d9\\u6eaa\",\"\\u7261\\u4e39\",\"\\u4e09\\u8c82\\u5dba\",\"\\u4faf\\u7850\",\"\\u745e\\u82b3\",\"\\u56db\\u8173\\u4ead\",\"\\u6696\\u6696\",\"\\u57fa\\u9686\",\"\\u4e09\\u5751\",\"\\u516b\\u5835\",\"\\u4e03\\u5835\",\"\\u767e\\u798f\",\"\\u4e94\\u5835\",\"\\u6c50\\u6b62\",\"\\u6c50\\u79d1\",\"\\u5357\\u6e2f\",\"\\u677e\\u5c71\",\"\\u53f0\\u5317\",\"\\u842c\\u83ef\",\"\\u677f\\u6a4b\",\"\\u6d6e\\u6d32\",\"\\u6a39\\u6797\",\"\\u5c71\\u4f73\",\"\\u9daf\\u6b4c\"]},{\"id\":\"1\",\"cname\":\"\\u6843\\u5712\\u5730\\u5340\",\"ename\":\"Tao-yuan\",\"stn\":[\"\\u6843\\u5712\",\"\\u5167\\u58e2\",\"\\u4e2d\\u58e2\",\"\\u57d4\\u5fc3\",\"\\u694a\\u6885\",\"\\u5bcc\\u5ca1\"]},{\"id\":\"2\",\"cname\":\"\\u65b0\\u7af9\\u5730\\u5340\",\"ename\":\"Hsin-chu  \",\"stn\":[\"\\u5317\\u6e56\",\"\\u6e56\\u53e3\",\"\\u65b0\\u8c50\",\"\\u7af9\\u5317\",\"\\u5317\\u65b0\\u7af9\",\"\\u65b0\\u7af9\",\"\\u9999\\u5c71\"]},{\"id\":\"3\",\"cname\":\"\\u82d7\\u6817\\u5730\\u5340\",\"ename\":\"Miao-li  \",\"stn\":[\"\\u5d0e\\u9802\",\"\\u7af9\\u5357\",\"\\u8ac7\\u6587\",\"\\u5927\\u5c71\",\"\\u5f8c\\u9f8d\",\"\\u9f8d\\u6e2f\",\"\\u767d\\u6c99\\u5c6f\",\"\\u65b0\\u57d4\",\"\\u901a\\u9704\",\"\\u82d1\\u88e1\",\"\\u9020\\u6a4b\",\"\\u8c50\\u5bcc\",\"\\u82d7\\u6817\",\"\\u5357\\u52e2\",\"\\u9285\\u947c\",\"\\u4e09\\u7fa9\"]},{\"id\":\"4\",\"cname\":\"\\u81fa\\u4e2d\\u5730\\u5340\",\"ename\":\"Taichung  \",\"stn\":[\"\\u65e5\\u5357\",\"\\u5927\\u7532\",\"\\u53f0\\u4e2d\\u6e2f\",\"\\u6e05\\u6c34\",\"\\u6c99\\u9e7f\",\"\\u9f8d\\u4e95\",\"\\u5927\\u809a\",\"\\u8ffd\\u5206\",\"\\u6cf0\\u5b89\",\"\\u540e\\u91cc\",\"\\u8c50\\u539f\",\"\\u6f6d\\u5b50\",\"\\u592a\\u539f\",\"\\u53f0\\u4e2d\",\"\\u5927\\u6176\",\"\\u70cf\\u65e5\",\"\\u65b0\\u70cf\\u65e5\",\"\\u6210\\u529f\"]},{\"id\":\"5\",\"cname\":\"\\u5f70\\u5316\\u5730\\u5340\",\"ename\":\"Chang-hua  \",\"stn\":[\"\\u5f70\\u5316\",\"\\u82b1\\u58c7\",\"\\u5927\\u6751\",\"\\u54e1\\u6797\",\"\\u6c38\\u9756\",\"\\u793e\\u982d\",\"\\u7530\\u4e2d\",\"\\u4e8c\\u6c34\"]},{\"id\":\"6\",\"cname\":\"\\u5357\\u6295\\u5730\\u5340\",\"ename\":\"NanTou\",\"stn\":[]},{\"id\":\"7\",\"cname\":\"\\u96f2\\u6797\\u5730\\u5340\",\"ename\":\"YunLin\",\"stn\":[\"\\u6797\\u5167\",\"\\u77f3\\u69b4\",\"\\u6597\\u516d\",\"\\u6597\\u5357\",\"\\u77f3\\u9f9c\"]},{\"id\":\"8\",\"cname\":\"\\u5609\\u7fa9\\u5730\\u5340\",\"ename\":\"Chia-yi  \",\"stn\":[\"\\u5927\\u6797\",\"\\u6c11\\u96c4\",\"\\u5609\\u5317\",\"\\u5609\\u7fa9\",\"\\u6c34\\u4e0a\",\"\\u5357\\u9756\"]},{\"id\":\"9\",\"cname\":\"\\u81fa\\u5357\\u5730\\u5340\",\"ename\":\"Tainan\",\"stn\":[\"\\u5f8c\\u58c1\",\"\\u65b0\\u71df\",\"\\u67f3\\u71df\",\"\\u6797\\u9cf3\\u71df\",\"\\u9686\\u7530\",\"\\u62d4\\u6797\",\"\\u5584\\u5316\",\"\\u5357\\u79d1\",\"\\u65b0\\u5e02\",\"\\u6c38\\u5eb7\",\"\\u5927\\u6a4b\",\"\\u53f0\\u5357\",\"\\u4fdd\\u5b89\",\"\\u4e2d\\u6d32\"]},{\"id\":\"10\",\"cname\":\"\\u9ad8\\u96c4\\u5730\\u5340\",\"ename\":\"Kaohsiung\",\"stn\":[\"\\u5927\\u6e56\",\"\\u8def\\u7af9\",\"\\u5ca1\\u5c71\",\"\\u6a4b\\u982d\",\"\\u6960\\u6893\",\"\\u65b0\\u5de6\\u71df\",\"\\u5de6\\u71df\",\"\\u9ad8\\u96c4\",\"\\u9cf3\\u5c71\",\"\\u5f8c\\u5e84\",\"\\u4e5d\\u66f2\\u5802\"]},{\"id\":\"11\",\"cname\":\"\\u5c4f\\u6771\\u5730\\u5340\",\"ename\":\"Ping-tung  \",\"stn\":[\"\\u516d\\u584a\\u539d\",\"\\u5c4f\\u6771\",\"\\u6b78\\u4f86\",\"\\u9e9f\\u6d1b\",\"\\u897f\\u52e2\",\"\\u7af9\\u7530\",\"\\u6f6e\\u5dde\",\"\\u5d01\\u9802\",\"\\u5357\\u5dde\",\"\\u93ae\\u5b89\",\"\\u6797\\u908a\",\"\\u4f73\\u51ac\",\"\\u6771\\u6d77\",\"\\u678b\\u5bee\",\"\\u52a0\\u797f\",\"\\u5167\\u7345\",\"\\u678b\\u5c71\"]},{\"id\":\"12\",\"cname\":\"\\u81fa\\u6771\\u5730\\u5340\",\"ename\":\"Taitung\",\"stn\":[\"\\u53e4\\u838a\",\"\\u5927\\u6b66\",\"\\u7027\\u6eaa\",\"\\u91d1\\u5d19\",\"\\u592a\\u9ebb\\u91cc\",\"\\u77e5\\u672c\",\"\\u5eb7\\u6a02\",\"\\u53f0\\u6771\",\"\\u5c71\\u91cc\",\"\\u9e7f\\u91ce\",\"\\u745e\\u6e90\",\"\\u745e\\u548c\",\"\\u6708\\u7f8e\",\"\\u95dc\\u5c71\",\"\\u6d77\\u7aef\",\"\\u6c60\\u4e0a\"]},{\"id\":\"13\",\"cname\":\"\\u82b1\\u84ee\\u5730\\u5340\",\"ename\":\"Hua-lien  \",\"stn\":[\"\\u5bcc\\u91cc\",\"\\u6771\\u7af9\",\"\\u6771\\u91cc\",\"\\u7389\\u91cc\",\"\\u4e09\\u6c11\",\"\\u745e\\u7a57\",\"\\u5bcc\\u6e90\",\"\\u5927\\u5bcc\",\"\\u5149\\u5fa9\",\"\\u842c\\u69ae\",\"\\u9cf3\\u6797\",\"\\u5357\\u5e73\",\"\\u6eaa\\u53e3\",\"\\u8c50\\u7530\",\"\\u58fd\\u8c50\",\"\\u5e73\\u548c\",\"\\u5fd7\\u5b78\",\"\\u5409\\u5b89\",\"\\u82b1\\u84ee\",\"\\u5317\\u57d4\",\"\\u666f\\u7f8e\",\"\\u65b0\\u57ce\",\"\\u5d07\\u5fb7\",\"\\u548c\\u4ec1\",\"\\u548c\\u5e73\"]},{\"id\":\"14\",\"cname\":\"\\u5b9c\\u862d\\u5730\\u5340\",\"ename\":\"Yi-lan  \",\"stn\":[\"\\u6f22\\u672c\",\"\\u6b66\\u5854\",\"\\u5357\\u6fb3\",\"\\u6771\\u6fb3\",\"\\u6c38\\u6a02\",\"\\u8607\\u6fb3\",\"\\u8607\\u6fb3\\u65b0\",\"\\u65b0\\u99ac\",\"\\u51ac\\u5c71\",\"\\u7f85\\u6771\",\"\\u4e2d\\u91cc\",\"\\u4e8c\\u7d50\",\"\\u5b9c\\u862d\",\"\\u56db\\u57ce\",\"\\u7901\\u6eaa\",\"\\u9802\\u57d4\",\"\\u982d\\u57ce\",\"\\u5916\\u6fb3\",\"\\u9f9c\\u5c71\",\"\\u5927\\u6eaa\",\"\\u5927\\u91cc\",\"\\u77f3\\u57ce\"]},{\"id\":\"15\",\"cname\":\"\\u5e73\\u6eaa\\u7dda\",\"ename\":\"Ping-hsi Line\",\"stn\":[\"\\u4e09\\u8c82\\u5dba\",\"\\u83c1\\u6850\",\"\\u5e73\\u6eaa\",\"\\u5dba\\u8173\",\"\\u671b\\u53e4\",\"\\u5341\\u5206\",\"\\u5927\\u83ef\"]},{\"id\":\"16\",\"cname\":\"\\u5167\\u7063\\/\\u516d\\u5bb6\\u7dda\",\"ename\":\"Nei-wan \\/ Liujia Line\",\"stn\":[\"\\u65b0\\u7af9\",\"\\u5317\\u65b0\\u7af9\",\"\\u4e16\\u535a(\\u5343\\u7532)\",\"\\u7af9\\u79d1(\\u65b0\\u838a)\",\"\\u7af9\\u4e2d\",\"\\u516d\\u5bb6\",\"\\u4e0a\\u54e1\",\"\\u69ae\\u83ef\",\"\\u7af9\\u6771\",\"\\u6a6b\\u5c71\",\"\\u4e5d\\u8b9a\\u982d\",\"\\u5408\\u8208\",\"\\u5bcc\\u8cb4\",\"\\u5167\\u7063\"]},{\"id\":\"17\",\"cname\":\"\\u96c6\\u96c6\\u7dda\",\"ename\":\"Jiji Line\",\"stn\":[\"\\u4e8c\\u6c34\",\"\\u6e90\\u6cc9\",\"\\u6fc1\\u6c34\",\"\\u9f8d\\u6cc9\",\"\\u96c6\\u96c6\",\"\\u6c34\\u91cc\",\"\\u8eca\\u57d5\"]},{\"id\":\"18\",\"cname\":\"\\u6c99\\u5d19\\u7dda\",\"ename\":\"Shalun Line\",\"stn\":[\"\\u9577\\u69ae\\u5927\\u5b78\",\"\\u6c99\\u5d19\"]}]";
    private static final String STATION_NUMBER = "{\"\\u57fa\\u9686\":\"1001\",\"\\u516b\\u5835\":\"1002\",\"\\u4e03\\u5835\":\"1003\",\"\\u4e94\\u5835\":\"1004\",\"\\u6c50\\u6b62\":\"1005\",\"\\u5357\\u6e2f\":\"1006\",\"\\u677e\\u5c71\":\"1007\",\"\\u53f0\\u5317\":\"1008\",\"\\u842c\\u83ef\":\"1009\",\"\\u677f\\u6a4b\":\"1011\",\"\\u6d6e\\u6d32\":\"1032\",\"\\u6a39\\u6797\":\"1012\",\"\\u5c71\\u4f73\":\"1013\",\"\\u9daf\\u6b4c\":\"1014\",\"\\u6843\\u5712\":\"1015\",\"\\u5167\\u58e2\":\"1016\",\"\\u4e2d\\u58e2\":\"1017\",\"\\u57d4\\u5fc3\":\"1018\",\"\\u694a\\u6885\":\"1019\",\"\\u5bcc\\u5ca1\":\"1020\",\"\\u5317\\u6e56\":\"1033\",\"\\u6e56\\u53e3\":\"1021\",\"\\u65b0\\u8c50\":\"1022\",\"\\u7af9\\u5317\":\"1023\",\"\\u5317\\u65b0\\u7af9\":\"1024\",\"\\u65b0\\u7af9\":\"1025\",\"\\u9999\\u5c71\":\"1026\",\"\\u5d0e\\u9802\":\"1027\",\"\\u7af9\\u5357\":\"1028\",\"\\u4e09\\u5751\":\"1029\",\"\\u767e\\u798f\":\"1030\",\"\\u6c50\\u79d1\":\"1031\",\"\\u8ac7\\u6587\":\"1102\",\"\\u8ac7\\u6587\\u5357\":\"1103\",\"\\u5927\\u5c71\":\"1104\",\"\\u5f8c\\u9f8d\":\"1105\",\"\\u9f8d\\u6e2f\":\"1106\",\"\\u767d\\u6c99\\u5c6f\":\"1107\",\"\\u65b0\\u57d4\":\"1108\",\"\\u901a\\u9704\":\"1109\",\"\\u82d1\\u88e1\":\"1110\",\"\\u65e5\\u5357\":\"1111\",\"\\u5927\\u7532\":\"1112\",\"\\u53f0\\u4e2d\\u6e2f\":\"1113\",\"\\u6e05\\u6c34\":\"1114\",\"\\u6c99\\u9e7f\":\"1115\",\"\\u9f8d\\u4e95\":\"1116\",\"\\u5927\\u809a\":\"1117\",\"\\u8ffd\\u5206\":\"1118\",\"\\u5927\\u809a\\u6eaa\\u5357\":\"1119\",\"\\u5f70\\u5316\":\"1120\",\"\\u82b1\\u58c7\":\"1202\",\"\\u54e1\\u6797\":\"1203\",\"\\u6c38\\u9756\":\"1204\",\"\\u793e\\u982d\":\"1205\",\"\\u7530\\u4e2d\":\"1206\",\"\\u4e8c\\u6c34\":\"1207\",\"\\u6797\\u5167\":\"1208\",\"\\u77f3\\u69b4\":\"1209\",\"\\u6597\\u516d\":\"1210\",\"\\u6597\\u5357\":\"1211\",\"\\u77f3\\u9f9c\":\"1212\",\"\\u5927\\u6797\":\"1213\",\"\\u6c11\\u96c4\":\"1214\",\"\\u5609\\u7fa9\":\"1215\",\"\\u6c34\\u4e0a\":\"1217\",\"\\u5357\\u9756\":\"1218\",\"\\u5f8c\\u58c1\":\"1219\",\"\\u65b0\\u71df\":\"1220\",\"\\u67f3\\u71df\":\"1221\",\"\\u6797\\u9cf3\\u71df\":\"1222\",\"\\u9686\\u7530\":\"1223\",\"\\u62d4\\u6797\":\"1224\",\"\\u5584\\u5316\":\"1225\",\"\\u65b0\\u5e02\":\"1226\",\"\\u6c38\\u5eb7\":\"1227\",\"\\u53f0\\u5357\":\"1228\",\"\\u4fdd\\u5b89\":\"1229\",\"\\u4e2d\\u6d32\":\"1230\",\"\\u5927\\u6e56\":\"1231\",\"\\u8def\\u7af9\":\"1232\",\"\\u5ca1\\u5c71\":\"1233\",\"\\u6a4b\\u982d\":\"1234\",\"\\u6960\\u6893\":\"1235\",\"\\u5de6\\u71df\":\"1236\",\"\\u9f13\\u5c71\":\"1237\",\"\\u9ad8\\u96c4\":\"1238\",\"\\u5927\\u6a4b\":\"1239\",\"\\u5927\\u6751\":\"1240\",\"\\u5609\\u5317\":\"1241\",\"\\u65b0\\u5de6\\u71df\":\"1242\",\"\\u4ec1\\u5fb7\":\"1243\",\"\\u9020\\u6a4b\":\"1302\",\"\\u8c50\\u5bcc\":\"1304\",\"\\u82d7\\u6817\":\"1305\",\"\\u5357\\u52e2\":\"1307\",\"\\u9285\\u947c\":\"1308\",\"\\u4e09\\u7fa9\":\"1310\",\"\\u6cf0\\u5b89\":\"1314\",\"\\u540e\\u91cc\":\"1315\",\"\\u8c50\\u539f\":\"1317\",\"\\u6f6d\\u5b50\":\"1318\",\"\\u53f0\\u4e2d\":\"1319\",\"\\u70cf\\u65e5\":\"1320\",\"\\u6210\\u529f\":\"1321\",\"\\u5927\\u6176\":\"1322\",\"\\u592a\\u539f\":\"1323\",\"\\u65b0\\u70cf\\u65e5\":\"1324\",\"\\u9cf3\\u5c71\":\"1402\",\"\\u5f8c\\u5e84\":\"1403\",\"\\u4e5d\\u66f2\\u5802\":\"1404\",\"\\u516d\\u584a\\u539d\":\"1405\",\"\\u5c4f\\u6771\":\"1406\",\"\\u6b78\\u4f86\":\"1407\",\"\\u9e9f\\u6d1b\":\"1408\",\"\\u897f\\u52e2\":\"1409\",\"\\u7af9\\u7530\":\"1410\",\"\\u6f6e\\u5dde\":\"1411\",\"\\u5d01\\u9802\":\"1412\",\"\\u5357\\u5dde\":\"1413\",\"\\u93ae\\u5b89\":\"1414\",\"\\u6797\\u908a\":\"1415\",\"\\u4f73\\u51ac\":\"1416\",\"\\u6771\\u6d77\":\"1417\",\"\\u678b\\u5bee\":\"1418\",\"\\u52a0\\u797f\":\"1502\",\"\\u5167\\u7345\":\"1503\",\"\\u678b\\u5c71\":\"1504\",\"\\u678b\\u91ce\":\"1505\",\"\\u4e2d\\u592e\":\"1506\",\"\\u53e4\\u838a\":\"1507\",\"\\u5927\\u6b66\":\"1508\",\"\\u7027\\u6eaa\":\"1510\",\"\\u591a\\u826f\":\"1511\",\"\\u91d1\\u5d19\":\"1512\",\"\\u592a\\u9ebb\\u91cc\":\"1514\",\"\\u77e5\\u672c\":\"1516\",\"\\u5eb7\\u6a02\":\"1517\",\"\\u5409\\u5b89\":\"1602\",\"\\u5fd7\\u5b78\":\"1604\",\"\\u5e73\\u548c\":\"1605\",\"\\u58fd\\u8c50\":\"1606\",\"\\u8c50\\u7530\":\"1607\",\"\\u6eaa\\u53e3\":\"1608\",\"\\u5357\\u5e73\":\"1609\",\"\\u9cf3\\u6797\":\"1610\",\"\\u842c\\u69ae\":\"1611\",\"\\u5149\\u5fa9\":\"1612\",\"\\u5927\\u5bcc\":\"1613\",\"\\u5bcc\\u6e90\":\"1614\",\"\\u745e\\u7a57\":\"1616\",\"\\u4e09\\u6c11\":\"1617\",\"\\u7389\\u91cc\":\"1619\",\"\\u5b89\\u901a\":\"1620\",\"\\u6771\\u91cc\":\"1621\",\"\\u6771\\u7af9\":\"1622\",\"\\u5bcc\\u91cc\":\"1623\",\"\\u6c60\\u4e0a\":\"1624\",\"\\u6d77\\u7aef\":\"1625\",\"\\u95dc\\u5c71\":\"1626\",\"\\u6708\\u7f8e\":\"1627\",\"\\u745e\\u548c\":\"1628\",\"\\u745e\\u6e90\":\"1629\",\"\\u9e7f\\u91ce\":\"1630\",\"\\u5c71\\u91cc\":\"1631\",\"\\u53f0\\u6771\":\"1632\",\"\\u99ac(\\u5ee2)\\u862d\":\"1633\",\"\\u53f0(\\u5ee2)\\u6771\":\"1634\",\"\\u821e\\u9db4\":\"1635\",\"\\u6c38\\u6a02\":\"1703\",\"\\u6771\\u6fb3\":\"1704\",\"\\u5357\\u6fb3\":\"1705\",\"\\u6b66\\u5854\":\"1706\",\"\\u6f22\\u672c\":\"1708\",\"\\u548c\\u5e73\":\"1709\",\"\\u548c\\u4ec1\":\"1710\",\"\\u5d07\\u5fb7\":\"1711\",\"\\u65b0\\u57ce\":\"1712\",\"\\u666f\\u7f8e\":\"1713\",\"\\u5317\\u57d4\":\"1714\",\"\\u82b1\\u84ee\":\"1715\",\"\\u6696\\u6696\":\"1802\",\"\\u56db\\u8173\\u4ead\":\"1803\",\"\\u745e\\u82b3\":\"1804\",\"\\u4faf\\u7850\":\"1805\",\"\\u4e09\\u8c82\\u5dba\":\"1806\",\"\\u7261\\u4e39\":\"1807\",\"\\u96d9\\u6eaa\":\"1808\",\"\\u8ca2\\u5bee\":\"1809\",\"\\u798f\\u9686\":\"1810\",\"\\u77f3\\u57ce\":\"1811\",\"\\u5927\\u91cc\":\"1812\",\"\\u5927\\u6eaa\":\"1813\",\"\\u9f9c\\u5c71\":\"1814\",\"\\u5916\\u6fb3\":\"1815\",\"\\u982d\\u57ce\":\"1816\",\"\\u9802\\u57d4\":\"1817\",\"\\u7901\\u6eaa\":\"1818\",\"\\u56db\\u57ce\":\"1819\",\"\\u5b9c\\u862d\":\"1820\",\"\\u4e8c\\u7d50\":\"1821\",\"\\u4e2d\\u91cc\":\"1822\",\"\\u7f85\\u6771\":\"1823\",\"\\u51ac\\u5c71\":\"1824\",\"\\u65b0\\u99ac\":\"1825\",\"\\u8607\\u6fb3\\u65b0\":\"1826\",\"\\u8607\\u6fb3\":\"1827\",\"\\u5927\\u83ef\":\"1903\",\"\\u5341\\u5206\":\"1904\",\"\\u671b\\u53e4\":\"1905\",\"\\u5dba\\u8173\":\"1906\",\"\\u5e73\\u6eaa\":\"1907\",\"\\u83c1\\u6850\":\"1908\",\"\\u6df1\\u6fb3\":\"2002\",\"\\u4e94\\u798f\":\"2102\",\"\\u6797\\u53e3\":\"2103\",\"\\u96fb\\u5ee0\":\"2104\",\"\\u6843\\u4e2d\":\"2105\",\"\\u5bf6\\u5c71\":\"2106\",\"\\u5357\\u7965\":\"2107\",\"\\u9577\\u8208\":\"2108\",\"\\u6d77\\u5c71\\u7ad9\":\"2109\",\"\\u6d77\\u6e56\\u7ad9\":\"2110\",\"\\u4e16\\u535a(\\u5343\\u7532)\":\"2212\",\"\\u7af9\\u79d1(\\u65b0\\u838a)\":\"2213\",\"\\u7af9\\u4e2d\":\"2203\",\"\\u516d\\u5bb6\":\"2214\",\"\\u4e0a\\u54e1\":\"2204\",\"\\u7af9\\u6771\":\"2205\",\"\\u6a6b\\u5c71\":\"2206\",\"\\u4e5d\\u8b9a\\u982d\":\"2207\",\"\\u5408\\u8208\":\"2208\",\"\\u5bcc\\u8cb4\":\"2209\",\"\\u5167\\u7063\":\"2210\",\"\\u69ae\\u83ef\":\"2211\",\"\\u53f0\\u4e2d\\u6e2f\\u8ca8\":\"2302\",\"\\u9f8d\\u4e95\\u7164\\u5834\":\"2402\",\"\\u795e\\u5ca1\":\"2502\",\"\\u6e90\\u6cc9\":\"2702\",\"\\u6fc1\\u6c34\":\"2703\",\"\\u9f8d\\u6cc9\":\"2704\",\"\\u96c6\\u96c6\":\"2705\",\"\\u6c34\\u91cc\":\"2706\",\"\\u8eca\\u57d5\":\"2707\",\"\\u5357\\u8abf\":\"2802\",\"\\u9ad8\\u96c4\\u6e2f\":\"2902\",\"\\u524d\\u93ae\":\"3102\",\"\\u82b1\\u84ee\\u6e2f\":\"3202\",\"\\u4e2d\\u8208\\u4e00\\u865f\":\"3302\",\"\\u4e2d\\u8208\\u4e8c\\u865f\":\"3402\",\"\\u6a5f\\u5ee0\":\"3902\",\"\\u6a39\\u8abf\":\"4102\",\"\\u6771\\u6e2f\\u652f\\u7dda\":\"4202\",\"\\u6771\\u5357\\u652f\\u7dda\":\"4302\",\"\\u5357\\u79d1\":\"1244\",\"\\u9577\\u69ae\\u5927\\u5b78\":\"5101\",\"\\u6c99\\u5d19\":\"5102\",\"rev\":{\"1001\":\"\\u57fa\\u9686\",\"1002\":\"\\u516b\\u5835\",\"1003\":\"\\u4e03\\u5835\",\"1004\":\"\\u4e94\\u5835\",\"1005\":\"\\u6c50\\u6b62\",\"1006\":\"\\u5357\\u6e2f\",\"1007\":\"\\u677e\\u5c71\",\"1008\":\"\\u53f0\\u5317\",\"1009\":\"\\u842c\\u83ef\",\"1011\":\"\\u677f\\u6a4b\",\"1032\":\"\\u6d6e\\u6d32\",\"1012\":\"\\u6a39\\u6797\",\"1013\":\"\\u5c71\\u4f73\",\"1014\":\"\\u9daf\\u6b4c\",\"1015\":\"\\u6843\\u5712\",\"1016\":\"\\u5167\\u58e2\",\"1017\":\"\\u4e2d\\u58e2\",\"1018\":\"\\u57d4\\u5fc3\",\"1019\":\"\\u694a\\u6885\",\"1020\":\"\\u5bcc\\u5ca1\",\"1033\":\"\\u5317\\u6e56\",\"1021\":\"\\u6e56\\u53e3\",\"1022\":\"\\u65b0\\u8c50\",\"1023\":\"\\u7af9\\u5317\",\"1024\":\"\\u5317\\u65b0\\u7af9\",\"1025\":\"\\u65b0\\u7af9\",\"1026\":\"\\u9999\\u5c71\",\"1027\":\"\\u5d0e\\u9802\",\"1028\":\"\\u7af9\\u5357\",\"1029\":\"\\u4e09\\u5751\",\"1030\":\"\\u767e\\u798f\",\"1031\":\"\\u6c50\\u79d1\",\"1102\":\"\\u8ac7\\u6587\",\"1103\":\"\\u8ac7\\u6587\\u5357\",\"1104\":\"\\u5927\\u5c71\",\"1105\":\"\\u5f8c\\u9f8d\",\"1106\":\"\\u9f8d\\u6e2f\",\"1107\":\"\\u767d\\u6c99\\u5c6f\",\"1108\":\"\\u65b0\\u57d4\",\"1109\":\"\\u901a\\u9704\",\"1110\":\"\\u82d1\\u88e1\",\"1111\":\"\\u65e5\\u5357\",\"1112\":\"\\u5927\\u7532\",\"1113\":\"\\u53f0\\u4e2d\\u6e2f\",\"1114\":\"\\u6e05\\u6c34\",\"1115\":\"\\u6c99\\u9e7f\",\"1116\":\"\\u9f8d\\u4e95\",\"1117\":\"\\u5927\\u809a\",\"1118\":\"\\u8ffd\\u5206\",\"1119\":\"\\u5927\\u809a\\u6eaa\\u5357\",\"1120\":\"\\u5f70\\u5316\",\"1202\":\"\\u82b1\\u58c7\",\"1203\":\"\\u54e1\\u6797\",\"1204\":\"\\u6c38\\u9756\",\"1205\":\"\\u793e\\u982d\",\"1206\":\"\\u7530\\u4e2d\",\"1207\":\"\\u4e8c\\u6c34\",\"1208\":\"\\u6797\\u5167\",\"1209\":\"\\u77f3\\u69b4\",\"1210\":\"\\u6597\\u516d\",\"1211\":\"\\u6597\\u5357\",\"1212\":\"\\u77f3\\u9f9c\",\"1213\":\"\\u5927\\u6797\",\"1214\":\"\\u6c11\\u96c4\",\"1215\":\"\\u5609\\u7fa9\",\"1217\":\"\\u6c34\\u4e0a\",\"1218\":\"\\u5357\\u9756\",\"1219\":\"\\u5f8c\\u58c1\",\"1220\":\"\\u65b0\\u71df\",\"1221\":\"\\u67f3\\u71df\",\"1222\":\"\\u6797\\u9cf3\\u71df\",\"1223\":\"\\u9686\\u7530\",\"1224\":\"\\u62d4\\u6797\",\"1225\":\"\\u5584\\u5316\",\"1226\":\"\\u65b0\\u5e02\",\"1227\":\"\\u6c38\\u5eb7\",\"1228\":\"\\u53f0\\u5357\",\"1229\":\"\\u4fdd\\u5b89\",\"1230\":\"\\u4e2d\\u6d32\",\"1231\":\"\\u5927\\u6e56\",\"1232\":\"\\u8def\\u7af9\",\"1233\":\"\\u5ca1\\u5c71\",\"1234\":\"\\u6a4b\\u982d\",\"1235\":\"\\u6960\\u6893\",\"1236\":\"\\u5de6\\u71df\",\"1237\":\"\\u9f13\\u5c71\",\"1238\":\"\\u9ad8\\u96c4\",\"1239\":\"\\u5927\\u6a4b\",\"1240\":\"\\u5927\\u6751\",\"1241\":\"\\u5609\\u5317\",\"1242\":\"\\u65b0\\u5de6\\u71df\",\"1243\":\"\\u4ec1\\u5fb7\",\"1302\":\"\\u9020\\u6a4b\",\"1304\":\"\\u8c50\\u5bcc\",\"1305\":\"\\u82d7\\u6817\",\"1307\":\"\\u5357\\u52e2\",\"1308\":\"\\u9285\\u947c\",\"1310\":\"\\u4e09\\u7fa9\",\"1314\":\"\\u6cf0\\u5b89\",\"1315\":\"\\u540e\\u91cc\",\"1317\":\"\\u8c50\\u539f\",\"1318\":\"\\u6f6d\\u5b50\",\"1319\":\"\\u53f0\\u4e2d\",\"1320\":\"\\u70cf\\u65e5\",\"1321\":\"\\u6210\\u529f\",\"1322\":\"\\u5927\\u6176\",\"1323\":\"\\u592a\\u539f\",\"1324\":\"\\u65b0\\u70cf\\u65e5\",\"1402\":\"\\u9cf3\\u5c71\",\"1403\":\"\\u5f8c\\u5e84\",\"1404\":\"\\u4e5d\\u66f2\\u5802\",\"1405\":\"\\u516d\\u584a\\u539d\",\"1406\":\"\\u5c4f\\u6771\",\"1407\":\"\\u6b78\\u4f86\",\"1408\":\"\\u9e9f\\u6d1b\",\"1409\":\"\\u897f\\u52e2\",\"1410\":\"\\u7af9\\u7530\",\"1411\":\"\\u6f6e\\u5dde\",\"1412\":\"\\u5d01\\u9802\",\"1413\":\"\\u5357\\u5dde\",\"1414\":\"\\u93ae\\u5b89\",\"1415\":\"\\u6797\\u908a\",\"1416\":\"\\u4f73\\u51ac\",\"1417\":\"\\u6771\\u6d77\",\"1418\":\"\\u678b\\u5bee\",\"1502\":\"\\u52a0\\u797f\",\"1503\":\"\\u5167\\u7345\",\"1504\":\"\\u678b\\u5c71\",\"1505\":\"\\u678b\\u91ce\",\"1506\":\"\\u4e2d\\u592e\",\"1507\":\"\\u53e4\\u838a\",\"1508\":\"\\u5927\\u6b66\",\"1510\":\"\\u7027\\u6eaa\",\"1511\":\"\\u591a\\u826f\",\"1512\":\"\\u91d1\\u5d19\",\"1514\":\"\\u592a\\u9ebb\\u91cc\",\"1516\":\"\\u77e5\\u672c\",\"1517\":\"\\u5eb7\\u6a02\",\"1602\":\"\\u5409\\u5b89\",\"1604\":\"\\u5fd7\\u5b78\",\"1605\":\"\\u5e73\\u548c\",\"1606\":\"\\u58fd\\u8c50\",\"1607\":\"\\u8c50\\u7530\",\"1608\":\"\\u6eaa\\u53e3\",\"1609\":\"\\u5357\\u5e73\",\"1610\":\"\\u9cf3\\u6797\",\"1611\":\"\\u842c\\u69ae\",\"1612\":\"\\u5149\\u5fa9\",\"1613\":\"\\u5927\\u5bcc\",\"1614\":\"\\u5bcc\\u6e90\",\"1616\":\"\\u745e\\u7a57\",\"1617\":\"\\u4e09\\u6c11\",\"1619\":\"\\u7389\\u91cc\",\"1620\":\"\\u5b89\\u901a\",\"1621\":\"\\u6771\\u91cc\",\"1622\":\"\\u6771\\u7af9\",\"1623\":\"\\u5bcc\\u91cc\",\"1624\":\"\\u6c60\\u4e0a\",\"1625\":\"\\u6d77\\u7aef\",\"1626\":\"\\u95dc\\u5c71\",\"1627\":\"\\u6708\\u7f8e\",\"1628\":\"\\u745e\\u548c\",\"1629\":\"\\u745e\\u6e90\",\"1630\":\"\\u9e7f\\u91ce\",\"1631\":\"\\u5c71\\u91cc\",\"1632\":\"\\u53f0\\u6771\",\"1633\":\"\\u99ac(\\u5ee2)\\u862d\",\"1634\":\"\\u53f0(\\u5ee2)\\u6771\",\"1635\":\"\\u821e\\u9db4\",\"1703\":\"\\u6c38\\u6a02\",\"1704\":\"\\u6771\\u6fb3\",\"1705\":\"\\u5357\\u6fb3\",\"1706\":\"\\u6b66\\u5854\",\"1708\":\"\\u6f22\\u672c\",\"1709\":\"\\u548c\\u5e73\",\"1710\":\"\\u548c\\u4ec1\",\"1711\":\"\\u5d07\\u5fb7\",\"1712\":\"\\u65b0\\u57ce\",\"1713\":\"\\u666f\\u7f8e\",\"1714\":\"\\u5317\\u57d4\",\"1715\":\"\\u82b1\\u84ee\",\"1802\":\"\\u6696\\u6696\",\"1803\":\"\\u56db\\u8173\\u4ead\",\"1804\":\"\\u745e\\u82b3\",\"1805\":\"\\u4faf\\u7850\",\"1806\":\"\\u4e09\\u8c82\\u5dba\",\"1807\":\"\\u7261\\u4e39\",\"1808\":\"\\u96d9\\u6eaa\",\"1809\":\"\\u8ca2\\u5bee\",\"1810\":\"\\u798f\\u9686\",\"1811\":\"\\u77f3\\u57ce\",\"1812\":\"\\u5927\\u91cc\",\"1813\":\"\\u5927\\u6eaa\",\"1814\":\"\\u9f9c\\u5c71\",\"1815\":\"\\u5916\\u6fb3\",\"1816\":\"\\u982d\\u57ce\",\"1817\":\"\\u9802\\u57d4\",\"1818\":\"\\u7901\\u6eaa\",\"1819\":\"\\u56db\\u57ce\",\"1820\":\"\\u5b9c\\u862d\",\"1821\":\"\\u4e8c\\u7d50\",\"1822\":\"\\u4e2d\\u91cc\",\"1823\":\"\\u7f85\\u6771\",\"1824\":\"\\u51ac\\u5c71\",\"1825\":\"\\u65b0\\u99ac\",\"1826\":\"\\u8607\\u6fb3\\u65b0\",\"1827\":\"\\u8607\\u6fb3\",\"1903\":\"\\u5927\\u83ef\",\"1904\":\"\\u5341\\u5206\",\"1905\":\"\\u671b\\u53e4\",\"1906\":\"\\u5dba\\u8173\",\"1907\":\"\\u5e73\\u6eaa\",\"1908\":\"\\u83c1\\u6850\",\"2002\":\"\\u6df1\\u6fb3\",\"2102\":\"\\u4e94\\u798f\",\"2103\":\"\\u6797\\u53e3\",\"2104\":\"\\u96fb\\u5ee0\",\"2105\":\"\\u6843\\u4e2d\",\"2106\":\"\\u5bf6\\u5c71\",\"2107\":\"\\u5357\\u7965\",\"2108\":\"\\u9577\\u8208\",\"2109\":\"\\u6d77\\u5c71\\u7ad9\",\"2110\":\"\\u6d77\\u6e56\\u7ad9\",\"2212\":\"\\u4e16\\u535a(\\u5343\\u7532)\",\"2213\":\"\\u7af9\\u79d1(\\u65b0\\u838a)\",\"2203\":\"\\u7af9\\u4e2d\",\"2214\":\"\\u516d\\u5bb6\",\"2204\":\"\\u4e0a\\u54e1\",\"2205\":\"\\u7af9\\u6771\",\"2206\":\"\\u6a6b\\u5c71\",\"2207\":\"\\u4e5d\\u8b9a\\u982d\",\"2208\":\"\\u5408\\u8208\",\"2209\":\"\\u5bcc\\u8cb4\",\"2210\":\"\\u5167\\u7063\",\"2211\":\"\\u69ae\\u83ef\",\"2302\":\"\\u53f0\\u4e2d\\u6e2f\\u8ca8\",\"2402\":\"\\u9f8d\\u4e95\\u7164\\u5834\",\"2502\":\"\\u795e\\u5ca1\",\"2702\":\"\\u6e90\\u6cc9\",\"2703\":\"\\u6fc1\\u6c34\",\"2704\":\"\\u9f8d\\u6cc9\",\"2705\":\"\\u96c6\\u96c6\",\"2706\":\"\\u6c34\\u91cc\",\"2707\":\"\\u8eca\\u57d5\",\"2802\":\"\\u5357\\u8abf\",\"2902\":\"\\u9ad8\\u96c4\\u6e2f\",\"3102\":\"\\u524d\\u93ae\",\"3202\":\"\\u82b1\\u84ee\\u6e2f\",\"3302\":\"\\u4e2d\\u8208\\u4e00\\u865f\",\"3402\":\"\\u4e2d\\u8208\\u4e8c\\u865f\",\"3902\":\"\\u6a5f\\u5ee0\",\"4102\":\"\\u6a39\\u8abf\",\"4202\":\"\\u6771\\u6e2f\\u652f\\u7dda\",\"4302\":\"\\u6771\\u5357\\u652f\\u7dda\",\"1244\":\"\\u5357\\u79d1\",\"5101\":\"\\u9577\\u69ae\\u5927\\u5b78\",\"5102\":\"\\u6c99\\u5d19\"}}";
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
//    private DrawerLayoutEdgeToggle mDrawerToggle;
////    private SearchView mSearchView;
//
//    private CharSequence mDrawerTitle;
//    private CharSequence mTitle;
//    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // update the main content by replacing fragments
        Fragment fragment = new StationFragment();
//        Bundle args = new Bundle();
////        args.putInt(StationFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        findViewById(R.id.ImageButton_Back).setOnClickListener(this);
//        mTitle = mDrawerTitle = getTitle();
//        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//
//        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        // set up the drawer's list view with items and click listener
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mPlanetTitles));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
//        ActionBar actionBar = getActionBar();
//
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
//        mDrawerToggle = new DrawerLayoutEdgeToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.drawable.ic_drawer, false, Gravity.LEFT);

//                (
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//        ) {
//            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            selectItem(0);
//        }

    }


//    private static void ttt()
//    {
//
//        TRStation.push('0');TRStation.push('1810');TRStation.push('福隆');TRStation.push('Fulong');TRStation.push('0');TRStation.push('1809');TRStation.push('貢寮');TRStation.push('Gongliao');TRStation.push('0');TRStation.push('1808');TRStation.push('雙溪');TRStation.push('Shuangxi');TRStation.push('0');TRStation.push('1807');TRStation.push('牡丹');TRStation.push('Mudan');TRStation.push('0');TRStation.push('1806');TRStation.push('三貂嶺');TRStation.push('Sandiaoling');TRStation.push('0');TRStation.push('1805');TRStation.push('猴硐');TRStation.push('Houtong');TRStation.push('0');TRStation.push('1804');TRStation.push('瑞芳');TRStation.push('Ruifang');TRStation.push('0');TRStation.push('1803');TRStation.push('四腳亭');TRStation.push('Sijiaoting');TRStation.push('0');TRStation.push('1802');TRStation.push('暖暖');TRStation.push('Nuannuan');TRStation.push('0');TRStation.push('1001');TRStation.push('基隆');TRStation.push('Keelung');TRStation.push('0');TRStation.push('1029');TRStation.push('三坑');TRStation.push('Sankeng');TRStation.push('0');TRStation.push('1002');TRStation.push('八堵');TRStation.push('Badu');TRStation.push('0');TRStation.push('1003');TRStation.push('七堵');TRStation.push('Qidu');TRStation.push('0');TRStation.push('1030');TRStation.push('百福');TRStation.push('Baifu');TRStation.push('0');TRStation.push('1004');TRStation.push('五堵');TRStation.push('Wudu');TRStation.push('0');TRStation.push('1005');TRStation.push('汐止');TRStation.push('Xizhi');TRStation.push('0');TRStation.push('1031');TRStation.push('汐科');TRStation.push('Xike');TRStation.push('0');TRStation.push('1006');TRStation.push('南港');TRStation.push('Nangang');TRStation.push('0');TRStation.push('1007');TRStation.push('松山');TRStation.push('Songshan');TRStation.push('0');TRStation.push('1008');TRStation.push('臺北');TRStation.push('Taipei');TRStation.push('0');TRStation.push('1009');TRStation.push('萬華');TRStation.push('Wanhua');TRStation.push('0');TRStation.push('1011');TRStation.push('板橋');TRStation.push('Banqiao');TRStation.push('0');TRStation.push('1032');TRStation.push('浮洲');TRStation.push('Fuzhou');TRStation.push('0');TRStation.push('1012');TRStation.push('樹林');TRStation.push('Shulin');TRStation.push('0');TRStation.push('1013');TRStation.push('山佳');TRStation.push('Shanjia');TRStation.push('0');TRStation.push('1014');TRStation.push('鶯歌');TRStation.push('Yingge');TRStation.push('1');TRStation.push('1015');TRStation.push('桃園');TRStation.push('Taoyuan');TRStation.push('1');TRStation.push('1016');TRStation.push('內壢');TRStation.push('Neili');TRStation.push('1');TRStation.push('1017');TRStation.push('中壢');TRStation.push('Zhongli');TRStation.push('1');TRStation.push('1018');TRStation.push('埔心');TRStation.push('Puxin');TRStation.push('1');TRStation.push('1019');TRStation.push('楊梅');TRStation.push('Yangmei');TRStation.push('1');TRStation.push('1020');TRStation.push('富岡');TRStation.push('Fugang');TRStation.push('10');TRStation.push('1231');TRStation.push('大湖');TRStation.push('Dahu');TRStation.push('10');TRStation.push('1232');TRStation.push('路竹');TRStation.push('Luzhu');TRStation.push('10');TRStation.push('1233');TRStation.push('岡山');TRStation.push('Gangshan');TRStation.push('10');TRStation.push('1234');TRStation.push('橋頭');TRStation.push('Qiaotou');TRStation.push('10');TRStation.push('1235');TRStation.push('楠梓');TRStation.push('Nanzi');TRStation.push('10');TRStation.push('1242');TRStation.push('新左營');TRStation.push('Xinzuoying');TRStation.push('10');TRStation.push('1236');TRStation.push('左營');TRStation.push('Zuoying');TRStation.push('10');TRStation.push('1238');TRStation.push('高雄');TRStation.push('Kaohsiung');TRStation.push('10');TRStation.push('1402');TRStation.push('鳳山');TRStation.push('Fengshan');TRStation.push('10');TRStation.push('1403');TRStation.push('後庄');TRStation.push('Houzhuang');TRStation.push('10');TRStation.push('1404');TRStation.push('九曲堂');TRStation.push('Jiuqutang');TRStation.push('11');TRStation.push('1405');TRStation.push('六塊厝');TRStation.push('Liukuaicuo');TRStation.push('11');TRStation.push('1406');TRStation.push('屏東');TRStation.push('Pingtung');TRStation.push('11');TRStation.push('1407');TRStation.push('歸來');TRStation.push('Guilai');TRStation.push('11');TRStation.push('1408');TRStation.push('麟洛');TRStation.push('Linluo');TRStation.push('11');TRStation.push('1409');TRStation.push('西勢');TRStation.push('Xishi');TRStation.push('11');TRStation.push('1410');TRStation.push('竹田');TRStation.push('Zhutian');TRStation.push('11');TRStation.push('1411');TRStation.push('潮州');TRStation.push('Chaozhou');TRStation.push('11');TRStation.push('1412');TRStation.push('崁頂');TRStation.push('Kanding');TRStation.push('11');TRStation.push('1413');TRStation.push('南州');TRStation.push('Nanzhou');TRStation.push('11');TRStation.push('1414');TRStation.push('鎮安');TRStation.push('Zhenan');TRStation.push('11');TRStation.push('1415');TRStation.push('林邊');TRStation.push('Linbian');TRStation.push('11');TRStation.push('1416');TRStation.push('佳冬');TRStation.push('Jiadong');TRStation.push('11');TRStation.push('1417');TRStation.push('東海');TRStation.push('Donghai');TRStation.push('11');TRStation.push('1418');TRStation.push('枋寮');TRStation.push('Fangliao');TRStation.push('11');TRStation.push('1502');TRStation.push('加祿');TRStation.push('Jialu');TRStation.push('11');TRStation.push('1503');TRStation.push('內獅');TRStation.push('Neishi');TRStation.push('11');TRStation.push('1504');TRStation.push('枋山');TRStation.push('Fangshan');TRStation.push('12');TRStation.push('1507');TRStation.push('古莊');TRStation.push('Guzhuang');TRStation.push('12');TRStation.push('1508');TRStation.push('大武');TRStation.push('Dawu');TRStation.push('12');TRStation.push('1510');TRStation.push('瀧溪');TRStation.push('Longxi');TRStation.push('12');TRStation.push('1512');TRStation.push('金崙');TRStation.push('Jinlun');TRStation.push('12');TRStation.push('1514');TRStation.push('太麻里');TRStation.push('Taimali');TRStation.push('12');TRStation.push('1516');TRStation.push('知本');TRStation.push('Zhiben');TRStation.push('12');TRStation.push('1517');TRStation.push('康樂');TRStation.push('Kangle');TRStation.push('12');TRStation.push('1632');TRStation.push('臺東');TRStation.push('Taitung');TRStation.push('12');TRStation.push('1631');TRStation.push('山里');TRStation.push('Shanli');TRStation.push('12');TRStation.push('1630');TRStation.push('鹿野');TRStation.push('Luye');TRStation.push('12');TRStation.push('1629');TRStation.push('瑞源');TRStation.push('Ruiyuan');TRStation.push('12');TRStation.push('1628');TRStation.push('瑞和');TRStation.push('Ruihe');TRStation.push('12');TRStation.push('1626');TRStation.push('關山');TRStation.push('Guanshan');TRStation.push('12');TRStation.push('1625');TRStation.push('海端');TRStation.push('Haiduan');TRStation.push('12');TRStation.push('1624');TRStation.push('池上');TRStation.push('Chishang');TRStation.push('13');TRStation.push('1623');TRStation.push('富里');TRStation.push('Fuli');TRStation.push('13');TRStation.push('1622');TRStation.push('東竹');TRStation.push('Dongzhu');TRStation.push('13');TRStation.push('1621');TRStation.push('東里');TRStation.push('Dongli');TRStation.push('13');TRStation.push('1619');TRStation.push('玉里');TRStation.push('Yuli');TRStation.push('13');TRStation.push('1617');TRStation.push('三民');TRStation.push('Sanmin');TRStation.push('13');TRStation.push('1616');TRStation.push('瑞穗');TRStation.push('Ruisui');TRStation.push('13');TRStation.push('1614');TRStation.push('富源');TRStation.push('Fuyuan');TRStation.push('13');TRStation.push('1613');TRStation.push('大富');TRStation.push('Dafu');TRStation.push('13');TRStation.push('1612');TRStation.push('光復');TRStation.push('Guangfu');TRStation.push('13');TRStation.push('1611');TRStation.push('萬榮');TRStation.push('Wanrong');TRStation.push('13');TRStation.push('1610');TRStation.push('鳳林');TRStation.push('Fenglin');TRStation.push('13');TRStation.push('1609');TRStation.push('南平');TRStation.push('Nanping');TRStation.push('13');TRStation.push('1607');TRStation.push('豐田');TRStation.push('Fengtian');TRStation.push('13');TRStation.push('1606');TRStation.push('壽豐');TRStation.push('Shoufeng');TRStation.push('13');TRStation.push('1605');TRStation.push('平和');TRStation.push('Pinghe');TRStation.push('13');TRStation.push('1604');TRStation.push('志學');TRStation.push('Zhixue');TRStation.push('13');TRStation.push('1602');TRStation.push('吉安');TRStation.push('Jian');TRStation.push('13');TRStation.push('1715');TRStation.push('花蓮');TRStation.push('Hualien');TRStation.push('13');TRStation.push('1714');TRStation.push('北埔');TRStation.push('Beipu');TRStation.push('13');TRStation.push('1713');TRStation.push('景美');TRStation.push('Jingmei');TRStation.push('13');TRStation.push('1712');TRStation.push('新城');TRStation.push('Xincheng');TRStation.push('13');TRStation.push('1711');TRStation.push('崇德');TRStation.push('Chongde');TRStation.push('13');TRStation.push('1710');TRStation.push('和仁');TRStation.push('Heren');TRStation.push('13');TRStation.push('1709');TRStation.push('和平');TRStation.push('Heping');TRStation.push('14');TRStation.push('1708');TRStation.push('漢本');TRStation.push('Hanben');TRStation.push('14');TRStation.push('1706');TRStation.push('武塔');TRStation.push('Wuta');TRStation.push('14');TRStation.push('1705');TRStation.push('南澳');TRStation.push('Nanao');TRStation.push('14');TRStation.push('1704');TRStation.push('東澳');TRStation.push('Dongao');TRStation.push('14');TRStation.push('1703');TRStation.push('永樂');TRStation.push('Yongle');TRStation.push('14');TRStation.push('1827');TRStation.push('蘇澳');TRStation.push('Suao');TRStation.push('14');TRStation.push('1826');TRStation.push('蘇澳新');TRStation.push('Suaoxin');TRStation.push('14');TRStation.push('1825');TRStation.push('新馬');TRStation.push('Xinma');TRStation.push('14');TRStation.push('1824');TRStation.push('冬山');TRStation.push('Dongshan');TRStation.push('14');TRStation.push('1823');TRStation.push('羅東');TRStation.push('Luodong');TRStation.push('14');TRStation.push('1822');TRStation.push('中里');TRStation.push('Zhongli');TRStation.push('14');TRStation.push('1821');TRStation.push('二結');TRStation.push('Erjie');TRStation.push('14');TRStation.push('1820');TRStation.push('宜蘭');TRStation.push('Yilan');TRStation.push('14');TRStation.push('1819');TRStation.push('四城');TRStation.push('Sicheng');TRStation.push('14');TRStation.push('1818');TRStation.push('礁溪');TRStation.push('Jiaoxi');TRStation.push('14');TRStation.push('1817');TRStation.push('頂埔');TRStation.push('Dingpu');TRStation.push('14');TRStation.push('1816');TRStation.push('頭城');TRStation.push('Toucheng');TRStation.push('14');TRStation.push('1815');TRStation.push('外澳');TRStation.push('Waiao');TRStation.push('14');TRStation.push('1814');TRStation.push('龜山');TRStation.push('Guishan');TRStation.push('14');TRStation.push('1813');TRStation.push('大溪');TRStation.push('Daxi');TRStation.push('14');TRStation.push('1812');TRStation.push('大里');TRStation.push('Dali');TRStation.push('14');TRStation.push('1811');TRStation.push('石城');TRStation.push('Shicheng');TRStation.push('15');TRStation.push('1908');TRStation.push('菁桐');TRStation.push('Jingtong');TRStation.push('15');TRStation.push('1907');TRStation.push('平溪');TRStation.push('Pingxi');TRStation.push('15');TRStation.push('1906');TRStation.push('嶺腳');TRStation.push('Lingjiao');TRStation.push('15');TRStation.push('1905');TRStation.push('望古');TRStation.push('Wanggu');TRStation.push('15');TRStation.push('1904');TRStation.push('十分');TRStation.push('Shifen');TRStation.push('15');TRStation.push('1903');TRStation.push('大華');TRStation.push('Dahua');TRStation.push('16');TRStation.push('2212');TRStation.push('千甲');TRStation.push('Qianjia');TRStation.push('16');TRStation.push('2213');TRStation.push('新莊');TRStation.push('Xinzhuang');TRStation.push('16');TRStation.push('2203');TRStation.push('竹中');TRStation.push('Zhuzhong');TRStation.push('16');TRStation.push('2214');TRStation.push('六家');TRStation.push('Liujia');TRStation.push('16');TRStation.push('2204');TRStation.push('上員');TRStation.push('Shangyuan');TRStation.push('16');TRStation.push('2211');TRStation.push('榮華');TRStation.push('Ronghua');TRStation.push('16');TRStation.push('2205');TRStation.push('竹東');TRStation.push('Zhudong');TRStation.push('16');TRStation.push('2206');TRStation.push('橫山');TRStation.push('Hengshan');TRStation.push('16');TRStation.push('2207');TRStation.push('九讚頭');TRStation.push('Jiuzantou');TRStation.push('16');TRStation.push('2208');TRStation.push('合興');TRStation.push('Hexing');TRStation.push('16');TRStation.push('2209');TRStation.push('富貴');TRStation.push('Fugui');TRStation.push('16');TRStation.push('2210');TRStation.push('內灣');TRStation.push('Neiwan');TRStation.push('17');TRStation.push('2702');TRStation.push('源泉');TRStation.push('Yuanquan');TRStation.push('17');TRStation.push('2703');TRStation.push('濁水');TRStation.push('Zhuoshui');TRStation.push('17');TRStation.push('2704');TRStation.push('龍泉');TRStation.push('Longquan');TRStation.push('17');TRStation.push('2705');TRStation.push('集集');TRStation.push('Jiji');TRStation.push('17');TRStation.push('2706');TRStation.push('水里');TRStation.push('Shuili');TRStation.push('17');TRStation.push('2707');TRStation.push('車埕');TRStation.push('Checheng');TRStation.push('18');TRStation.push('5101');TRStation.push('長榮大學');TRStation.push('Chang Jung Christian University');TRStation.push('18');TRStation.push('5102');TRStation.push('沙崙');TRStation.push('Shalun');TRStation.push('19');TRStation.push('6103');TRStation.push('海科館');TRStation.push('NMMST');TRStation.push('2');TRStation.push('1021');TRStation.push('湖口');TRStation.push('Hukou');TRStation.push('2');TRStation.push('1022');TRStation.push('新豐');TRStation.push('Xinfeng');TRStation.push('2');TRStation.push('1023');TRStation.push('竹北');TRStation.push('Zhubei');TRStation.push('2');TRStation.push('1024');TRStation.push('北新竹');TRStation.push('North Hsinchu');TRStation.push('2');TRStation.push('1025');TRStation.push('新竹');TRStation.push('Hsinchu');TRStation.push('2');TRStation.push('1026');TRStation.push('香山');TRStation.push('Xiangshan');TRStation.push('2');TRStation.push('1033');TRStation.push('北湖');TRStation.push('Beihu(China University of Technology)');TRStation.push('3');TRStation.push('1027');TRStation.push('崎頂');TRStation.push('Qiding');TRStation.push('3');TRStation.push('1028');TRStation.push('竹南');TRStation.push('Zhunan');TRStation.push('3');TRStation.push('1102');TRStation.push('談文');TRStation.push('Tanwen');TRStation.push('3');TRStation.push('1104');TRStation.push('大山');TRStation.push('Dashan');TRStation.push('3');TRStation.push('1105');TRStation.push('後龍');TRStation.push('Houlong');TRStation.push('3');TRStation.push('1106');TRStation.push('龍港');TRStation.push('Longgang');TRStation.push('3');TRStation.push('1107');TRStation.push('白沙屯');TRStation.push('Baishatun');TRStation.push('3');TRStation.push('1108');TRStation.push('新埔');TRStation.push('Xinpu');TRStation.push('3');TRStation.push('1109');TRStation.push('通霄');TRStation.push('Tongxiao');TRStation.push('3');TRStation.push('1110');TRStation.push('苑裡');TRStation.push('Yuanli');TRStation.push('3');TRStation.push('1302');TRStation.push('造橋');TRStation.push('Zaoqiao');TRStation.push('3');TRStation.push('1304');TRStation.push('豐富');TRStation.push('Fengfu');TRStation.push('3');TRStation.push('1305');TRStation.push('苗栗');TRStation.push('Miaoli');TRStation.push('3');TRStation.push('1307');TRStation.push('南勢');TRStation.push('Nanshi');TRStation.push('3');TRStation.push('1308');TRStation.push('銅鑼');TRStation.push('Tongluo');TRStation.push('3');TRStation.push('1310');TRStation.push('三義');TRStation.push('Sanyi');TRStation.push('4');TRStation.push('1111');TRStation.push('日南');TRStation.push('Rinan');TRStation.push('4');TRStation.push('1112');TRStation.push('大甲');TRStation.push('Dajia');TRStation.push('4');TRStation.push('1113');TRStation.push('臺中港');TRStation.push('Taichung Port');TRStation.push('4');TRStation.push('1114');TRStation.push('清水');TRStation.push('Qingshui');TRStation.push('4');TRStation.push('1115');TRStation.push('沙鹿');TRStation.push('Shalu');TRStation.push('4');TRStation.push('1116');TRStation.push('龍井');TRStation.push('Longjing');TRStation.push('4');TRStation.push('1117');TRStation.push('大肚');TRStation.push('Dadu');TRStation.push('4');TRStation.push('1118');TRStation.push('追分');TRStation.push('Zhuifen');TRStation.push('4');TRStation.push('1314');TRStation.push('泰安');TRStation.push('Taian');TRStation.push('4');TRStation.push('1315');TRStation.push('后里');TRStation.push('Houli');TRStation.push('4');TRStation.push('1317');TRStation.push('豐原');TRStation.push('Fengyuan');TRStation.push('4');TRStation.push('1318');TRStation.push('潭子');TRStation.push('Tanzi');TRStation.push('4');TRStation.push('1323');TRStation.push('太原');TRStation.push('Taiyuan');TRStation.push('4');TRStation.push('1319');TRStation.push('臺中');TRStation.push('Taichung');TRStation.push('4');TRStation.push('1322');TRStation.push('大慶');TRStation.push('Daqing');TRStation.push('4');TRStation.push('1320');TRStation.push('烏日');TRStation.push('Wuri');TRStation.push('4');TRStation.push('1324');TRStation.push('新烏日');TRStation.push('Xinwuri');TRStation.push('4');TRStation.push('1321');TRStation.push('成功');TRStation.push('Chenggong');TRStation.push('5');TRStation.push('1120');TRStation.push('彰化');TRStation.push('Changhua');TRStation.push('5');TRStation.push('1202');TRStation.push('花壇');TRStation.push('Huatan');TRStation.push('5');TRStation.push('1240');TRStation.push('大村');TRStation.push('Dacun');TRStation.push('5');TRStation.push('1203');TRStation.push('員林');TRStation.push('Yuanlin');TRStation.push('5');TRStation.push('1204');TRStation.push('永靖');TRStation.push('Yongjing');TRStation.push('5');TRStation.push('1205');TRStation.push('社頭');TRStation.push('Shetou');TRStation.push('5');TRStation.push('1206');TRStation.push('田中');TRStation.push('Tianzhong');TRStation.push('5');TRStation.push('1207');TRStation.push('二水');TRStation.push('Ershui');TRStation.push('7');TRStation.push('1208');TRStation.push('林內');TRStation.push('Linnei');TRStation.push('7');TRStation.push('1209');TRStation.push('石榴');TRStation.push('Shiliu');TRStation.push('7');TRStation.push('1210');TRStation.push('斗六');TRStation.push('Douliu');TRStation.push('7');TRStation.push('1211');TRStation.push('斗南');TRStation.push('Dounan');TRStation.push('7');TRStation.push('1212');TRStation.push('石龜');TRStation.push('Shigui');TRStation.push('8');TRStation.push('1213');TRStation.push('大林');TRStation.push('Dalin');TRStation.push('8');TRStation.push('1214');TRStation.push('民雄');TRStation.push('Minxiong');TRStation.push('8');TRStation.push('1241');TRStation.push('嘉北');TRStation.push('Jiabei');TRStation.push('8');TRStation.push('1215');TRStation.push('嘉義');TRStation.push('Chiayi');TRStation.push('8');TRStation.push('1217');TRStation.push('水上');TRStation.push('Shuishang');TRStation.push('8');TRStation.push('1218');TRStation.push('南靖');TRStation.push('Nanjing');TRStation.push('9');TRStation.push('1219');TRStation.push('後壁');TRStation.push('Houbi');TRStation.push('9');TRStation.push('1220');TRStation.push('新營');TRStation.push('Xinying');TRStation.push('9');TRStation.push('1221');TRStation.push('柳營');TRStation.push('Liuying');TRStation.push('9');TRStation.push('1222');TRStation.push('林鳳營');TRStation.push('Linfengying');TRStation.push('9');TRStation.push('1223');TRStation.push('隆田');TRStation.push('Longtian');TRStation.push('9');TRStation.push('1224');TRStation.push('拔林');TRStation.push('Balin');TRStation.push('9');TRStation.push('1225');TRStation.push('善化');TRStation.push('Shanhua');TRStation.push('9');TRStation.push('1244');TRStation.push('南科');TRStation.push('Nanke');TRStation.push('9');TRStation.push('1226');TRStation.push('新市');TRStation.push('Xinshi');TRStation.push('9');TRStation.push('1227');TRStation.push('永康');TRStation.push('Yongkang');TRStation.push('9');TRStation.push('1239');TRStation.push('大橋');TRStation.push('Daqiao');TRStation.push('9');TRStation.push('1228');TRStation.push('臺南');TRStation.push('Tainan');TRStation.push('9');TRStation.push('1229');TRStation.push('保安');TRStation.push('Baoan');TRStation.push('9');TRStation.push('1243');TRStation.push('仁德');TRStation.push('Rende');TRStation.push('9');TRStation.push('1230');TRStation.push('中洲');TRStation.push('Zhongzhou');
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        mSearchView = (SearchView) searchItem.getActionView();
//        setupSearchView(searchItem);
//        return super.onCreateOptionsMenu(menu);
//    }

//    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

//    private void setupSearchView(MenuItem searchItem)
//    {
//
//        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null)
//        {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables)
//            {
//                if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }
//
//        mSearchView.setOnQueryTextListener(this);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // The action bar home/up action should open or close the drawer.
//        // ActionBarDrawerToggle will take care of this.
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle action buttons
//        switch(item.getItemId()) {
//            case R.id.action_direction:
//                // create intent to perform web search for this planet
////                getFragmentManager().s
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String s)
//    {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s)
//    {
//        return false;
//    }
//
//    @Override
//    public void onClick(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.ImageButton_Back:
////                mDrawerLayout.;
//                break;
//
//        }
//    }
//
//    /* The click listner for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    private void selectItem(int position) {
//        // update the main content by replacing fragments
//        Fragment fragment = new StationFragment();
//        Bundle args = new Bundle();
////        args.putInt(StationFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//        // update selected item and title, then close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//    }

//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
//    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class StationFragment extends Fragment {
//        public static final String ARG_PLANET_NUMBER = "planet_number";

        private static final String[] MAP_KEY = new String[]{
                "type",
                "code",
                "time",
                "end_station",
                "direction",
                "delay"
        };
        private static final String[] FROM_KEY = new String[]{
                "type",
                "code",
                "time",
                "end_station",
                "delay"
        };
        private static final int[] RESOURCE_ID = new int[]{
                R.id.Text_Type,
                R.id.Text_Code,
                R.id.Text_Time,
                R.id.Text_EndStation,
                R.id.Text_Delay
        };
        private WeakReference<ListView> mListView;
        private SimpleAdapter mSimpleAdapter0;
        private SimpleAdapter mSimpleAdapter1;
        private ArrayList<HashMap<String, String>> mData0 = new ArrayList<HashMap<String, String>>();
        private ArrayList<HashMap<String, String>> mData1 = new ArrayList<HashMap<String, String>>();
        public StationFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
//            int i = getArguments().getInt(ARG_PLANET_NUMBER);
//            String planet = getResources().getStringArray(R.array.planets_array)[i];

//            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//                    "drawable", getActivity().getPackageName());
            mListView = new WeakReference<ListView>(((ListView) rootView.findViewById(R.id.ListView)));
//            ((TextView) rootView.findViewById(R.id.text)).setText("");

            mSimpleAdapter0 = new SimpleAdapter(getActivity(), mData0, R.layout.fragment_list_item, FROM_KEY, RESOURCE_ID);
            mSimpleAdapter1 = new SimpleAdapter(getActivity(), mData1, R.layout.fragment_list_item, FROM_KEY, RESOURCE_ID);
            mListView.get().setAdapter(mSimpleAdapter0);
//            getActivity().setTitle(planet);
            loadText();
            return rootView;
        }

        private void loadText()
        {
            new Thread()
            {
                @Override
                public void run()
                {

                    try {
                        HttpURLConnection c = (HttpURLConnection) new URL(URL_FORMAT).openConnection();

                        if(c.getResponseCode() != HttpStatus.SC_OK){ return; }

                        String ret = readStream(c.getInputStream());
                        c.disconnect();
                        if(ret == null){ return; }

                        Pattern p = Pattern.compile("TRSearchResult\\.push\\('[^']*'\\);");
                        Matcher m = p.matcher(ret);
                        int i = 0;
                        HashMap<String, String> data = null;
                        int row = 0;
                        while (m.find())
                        {
                            row = i % 6;
                            String group = m.group();
                            String item = group.substring(21, group.length() - 3);
                            switch (row)
                            {
                                case 0:
                                    data = new HashMap<String, String>();
                                case 4:
                                    if (item.equals("0"))
                                    {
                                        mData0.add(data);
                                    }
                                    else
                                    {
                                        mData1.add(data);
                                    }
                                    break;
                                case 5:
                                    if (item.length() > 0)
                                    {
                                        try
                                        {
                                            int delay = Integer.parseInt(item);
                                            if (delay == 0)
                                            {
                                                item = "準點";
                                            }
                                            else
                                            {
                                                item = String.format("晚%d分",delay);
                                            }
                                        }catch (Exception e){}
                                    }
                                    break;
                            }
                            data.put(MAP_KEY[row], item);
                            i++;
                        }
                        final ListView listView = mListView.get();
                        if (listView != null)
                        {
                            listView.post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mSimpleAdapter0.notifyDataSetChanged();
                                    mSimpleAdapter1.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
        }


    }

    private static String readStream(InputStream inputStream)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line=reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class TRCity
    {
        private static final TRCity[] CITYS = new TRCity[20];
        static
        {
            CITYS[0] = new TRCity(0, "臺北地區", "Taipei");
            CITYS[1] = new TRCity(1, "桃園地區", "Taoyuan");
            CITYS[2] = new TRCity(2, "新竹地區", "Hsinchu  ");
            CITYS[3] = new TRCity(3, "苗栗地區", "Miaoli  ");
            CITYS[4] = new TRCity(4, "臺中地區", "Taichung  ");
            CITYS[5] = new TRCity(5, "彰化地區", "Changhua  ");
            CITYS[6] = new TRCity(6, "南投地區", "Nantou");
            CITYS[7] = new TRCity(7, "雲林地區", "Yunlin");
            CITYS[8] = new TRCity(8, "嘉義地區", "Chiayi  ");
            CITYS[9] = new TRCity(9, "臺南地區", "Tainan");
            CITYS[10] = new TRCity(10, "高雄地區", "Kaohsiung");
            CITYS[11] = new TRCity(11, "屏東地區", "Pingtung  ");
            CITYS[12] = new TRCity(12, "臺東地區", "Taitung");
            CITYS[13] = new TRCity(13, "花蓮地區", "Hualien  ");
            CITYS[14] = new TRCity(14, "宜蘭地區", "Yilan  ");
            CITYS[15] = new TRCity(15, "平溪線", "Pinghsi Line");
            CITYS[16] = new TRCity(16, "內灣/六家線", "Neiwan / Liujia Line");
            CITYS[17] = new TRCity(17, "集集線", "Jiji Line");
            CITYS[18] = new TRCity(18, "沙崙線", "Shalun Line");
            CITYS[19] = new TRCity(19, "深澳線", "ShenAo Line");
        }

        private int index;
        private String area;
        private String english;

//        private st
        private TRCity(int index, String area, String english)
        {
            this.index = index;
            this.area = area;
            this.english = english;
        }
    }

    private static class TRStation
    {
        static
        {

        }

    }

}
