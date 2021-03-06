/*
 * Copyright 2015 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.comcast.cdn.traffic_control.traffic_router.neustar.data;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HttpClient {
	private final Logger LOGGER = Logger.getLogger(HttpClient.class);

	private CloseableHttpClient httpClient;

	public CloseableHttpResponse execute(HttpUriRequest request) {
		try {
			httpClient = HttpClientBuilder.create().build();
			return httpClient.execute(request);
		} catch (IOException e) {
			LOGGER.warn("Failed to execute http request " + request.getMethod() + " " + request.getURI() + ": " + e.getMessage());
			try {
				httpClient.close();
			} catch (IOException e1) {
				LOGGER.warn("After exception, Failed to close Http Client " + e1.getMessage());
			}
			return null;
		}
	}

	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			LOGGER.warn("Failed to close Http Client " + e.getMessage());
		}
	}
}
