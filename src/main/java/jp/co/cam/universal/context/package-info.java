/**
 * This package provides functionality to manage system and users state.
 * <ul>
 *  <li>Manage user information.</li>
 *  <li>Carry objects using thread-local functionality.<br>
 *   <pre>
 *    This feature also allows the UserInfo class to be referenced anywhere in the thread.
 *    (*)Make sure to clear UserContext, when using this function.
 *   </pre>
 *  </li>
 * </ul>
 * @see jp.co.cam.universal.context.UserInfoFactory
 * @see jp.co.cam.universal.context.UserInfoIF
 */
package jp.co.cam.universal.context;
