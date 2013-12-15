/*
*  Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.launcher;

import org.wso2.carbon.launcher.config.CarbonLaunchConfig;
import org.wso2.carbon.launcher.utils.Utils;

import java.io.File;

import static org.wso2.carbon.launcher.utils.Constants.*;

public class Main {

//    private static Log log = LogFactory.getLog(Main.class);
    // TODO handle restarts and error handling here.

    /**
     * @param args arguments
     */
    public static void main(String[] args) {

        // 1) Initialize and/or verify System properties
        initAndVerifySysProps();

        // 2) Initialize logging.
        //TODO

        // 3) Load the Carbon start configuration
        String launchPropFilePath = Utils.getRepositoryConfDir() + File.separator + "osgi" +
                File.separator + LAUNCH_PROPERTIES_FILE;
        File launchPropFile = new File(launchPropFilePath);

        CarbonLaunchConfig<String, String> config;
        if (launchPropFile.exists()) {
            config = new CarbonLaunchConfig<String, String>(launchPropFile);
        } else {
            config = new CarbonLaunchConfig<String, String>();
        }

        CarbonServer carbonServer = new CarbonServer(config);

        // 3 Register a shutdown hook to stop the server
        registerShutdownHook(carbonServer);

        // 4) Start Carbon server.
        try {
            // This method launches the OSGi framework, loads all the bundles and starts Carbon server completely.
            carbonServer.start();
            System.exit(0);
        } catch (Throwable e) {
            // We need to invoke the stop method of the CarbonServer to allow the server to cleanup itself.
            carbonServer.stop();
            System.exit(-1);
        }
    }

    private static void registerShutdownHook(final CarbonServer carbonServer) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    carbonServer.stop();
                } catch (Throwable e) {
                    System.exit(-1);
                }
            }
        });
    }

    private static void initAndVerifySysProps() {
        String carbonHome = System.getProperty(CARBON_HOME);
        if (carbonHome == null || carbonHome.length() == 0) {
            throw new RuntimeException("carbon.home system property must be set before starting the server");
        }

        String profileName = System.getProperty(PROFILE);
        if (profileName == null || profileName.length() == 0) {
            System.setProperty(PROFILE, DEFAULT_PROFILE);
        }

        System.setProperty(LOGGING_DEFAULT_SERVICE_NAME, PAX_LOGGING_LEVEL);
        System.setProperty(BUNDLE_CONFIG_LOCATION, Utils.getRepositoryConfDir() + File.separator + "logging-config");
    }
}