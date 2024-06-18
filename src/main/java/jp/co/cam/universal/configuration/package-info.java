/**
 * This package provides the ability to reference resource files such as settings.
 * <ul>
 *  <li>Loading any properties file(Supported character code is UTF-8).</li>
 *  <li>multi-language support
 *   <pre>
 *    File switching function according to the user's country and language.
 *    (*)This feature uses UserInfo class.
 *    
 *    (example)sample_en_US.properties &gt; sample_en.properties &gt; sample_US.properties &gt; sample.properties
 *   </pre>
 *  </li>
 *  <li>This feature is also used in my setting file(/common.properties).</li>
 * </ul>
 * @see jp.co.cam.universal.context.UserInfoFactory
 * @see jp.co.cam.universal.context.UserInfoIF
 */
package jp.co.cam.universal.configuration;
