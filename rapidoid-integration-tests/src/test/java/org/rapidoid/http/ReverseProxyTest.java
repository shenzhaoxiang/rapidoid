package org.rapidoid.http;

/*
 * #%L
 * rapidoid-integration-tests
 * %%
 * Copyright (C) 2014 - 2016 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.Test;
import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.setup.On;

@Authors("Nikolche Mihajlovski")
@Since("5.2.3")
public class ReverseProxyTest extends IsolatedIntegrationTest {

	@Test
	public void testRoutingPerPath() {

		On.get("/foo").json("FOO");
		On.get("/foo/hi").json("FOO HI");
		On.get("/foo/hello").json("FOO HELLO");

		On.get("/bar/hi").json("BAR HI");

		proxy("/bar", "http://localhost:8888/foo");
		proxy("/", "http://localhost:8888/foo");

		getReq("/bar");
		getReq("/bar/hello/");
		getReq("/bar/hi");

		getReq("/");
		getReq("/hello/");
		getReq("/hi");
	}

}
