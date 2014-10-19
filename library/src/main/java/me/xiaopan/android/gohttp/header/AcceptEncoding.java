/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.gohttp.header;

/**
 * 接受的文件类型
 */
public class AcceptEncoding extends HttpHeader {
	/**
	 * 名字
	 */
	public static final String NAME = "Accept-Encoding";
	/**
	 * 值
	 */
	private String value;
	
	public AcceptEncoding(String value) {
		setValue(value);
	}
	
	public AcceptEncoding() {
		setValue("deflate, gzip, x-gzip, identity, *;q=0");
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}