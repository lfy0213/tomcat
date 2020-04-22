/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat;

import javax.servlet.ServletContext;

/**
 * Scans a web application and classloader hierarchy for JAR files. Uses
 * include TLD scanning and web-fragment.xml scanning. Uses a call-back
 * mechanism so the caller can process each JAR found.
 *
 * 用于扫描Context对应的Web应用的Jar包。
 * 每个Web应用初始化时，在对TLD文件和web-fragment.xml文件处理时都需要对该Web应用下的Jar包进行扫描，因为Jar包可能包含这些配置文件，Web容器需要对它们进行处理。
 *
 * 标准实现为StandardJarScanner，它将对Web应用的WEB-INF/lib目录的Jar包进行扫描，它支持声明忽略某些Jar包
 * 同时它还支持对classpath下的Jar包进行扫描。然而，如果classpath下的Jar包与WEB-INF/lib目录下的Jar包相同
 *
 * JarScanner在设计上采用了回调机制，每扫描到一个Jar包时都会调用回调对象进行处理，回调对象需要实现JarScannerCallback接口
 * JarScanner在扫描到每个Jar包后都会调用一次此方法，执行对该Jar包的逻辑处理。
 */
public interface JarScanner {

    /**
     * Scan the provided ServletContext and classloader for JAR files. Each JAR
     * file found will be passed to the callback handler to be processed.
     *
     * @param scanType      The type of JAR scan to perform. This is passed to
     *                          the filter which uses it to determine how to
     *                          filter the results
     * @param context       The ServletContext - used to locate and access
     *                      WEB-INF/lib
     * @param callback      The handler to process any JARs found
     */
    public void scan(JarScanType scanType, ServletContext context,
            JarScannerCallback callback);

    public JarScanFilter getJarScanFilter();

    public void setJarScanFilter(JarScanFilter jarScanFilter);
}
