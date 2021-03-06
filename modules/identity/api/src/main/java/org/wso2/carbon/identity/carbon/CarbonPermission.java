/*
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package org.wso2.carbon.identity.carbon;

public class CarbonPermission {

	private String resource;
	private String action;
//	private PermissionIdentifier permissionIdentifier;
	private String description;

	/**
	 * 
	 * @param resource
	 * @param action
	 */
	public CarbonPermission(String resource, String action) {
		this.resource = resource;
		this.action = action;
		this.description = "";
	}

	/**
	 * 
	 * @return
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

}