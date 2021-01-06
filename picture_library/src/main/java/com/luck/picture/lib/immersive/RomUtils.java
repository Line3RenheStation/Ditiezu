/*
 * ==================================================
 * =  PROJECT     地下铁的故事
 * =  MODULE      地下铁的故事.picture_library
 * =  FILE NAME   RomUtils
 * =  LAST MODIFIED BY PASSIONPENGUIN [1/5/21, 9:25 PM]
 * ==================================================
 * Copyright 2021 PassionPenguin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.luck.picture.lib.immersive;

import android.os.Build;
import android.text.TextUtils;

import com.luck.picture.lib.tools.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author：luck
 * @data：2018/3/28 下午1:02
 * @描述: Rom版本管理
 */

public class RomUtils {
    private static Integer romType;

    public static int getLightStatausBarAvailableRomType() {
        if (romType != null) {
            return romType;
        }

        if (isMIUIV6OrAbove()) {
            romType = AvailableRomType.MIUI;
            return romType;
        }

        if (isFlymeV4OrAbove()) {
            romType = AvailableRomType.FLYME;
            return romType;
        }

        if (isAndroid5OrAbove()) {
            romType = AvailableRomType.ANDROID_NATIVE;
            return romType;
        }

        romType = AvailableRomType.NA;
        return romType;
    }

    //Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
    //Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
    private static boolean isFlymeV4OrAbove() {
        return (getFlymeVersion() >= 4);
    }

    //Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
    //Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
    public static int getFlymeVersion() {
        String displayId = Build.DISPLAY;
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            displayId = displayId.replaceAll("Flyme", "");
            displayId = displayId.replaceAll("OS", "");
            displayId = displayId.replaceAll(" ", "");


            String version = displayId.substring(0, 1);

            if (version != null) {
                return StringUtils.stringToInt(version);
            }
        }
        return 0;
    }

    //MIUI V6对应的versionCode是4
    //MIUI V7对应的versionCode是5
    private static boolean isMIUIV6OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                if (miuiVersionCode >= 4) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static int getMIUIVersionCode() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        int miuiVersionCode = 0;
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                return miuiVersionCode;
            } catch (Exception e) {
            }
        }
        return miuiVersionCode;
    }

    //Android Api 23以上
    private static boolean isAndroid5OrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }

    public class AvailableRomType {
        public static final int MIUI = 1;
        public static final int FLYME = 2;
        public static final int ANDROID_NATIVE = 3;
        public static final int NA = 4;
    }

}