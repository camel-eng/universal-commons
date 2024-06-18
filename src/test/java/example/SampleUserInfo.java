package example;

import jp.co.cam.universal.context.impl.DefaultUserInfo;

/**
 * (examples)How to add user information items.
 * It's also possible to implement any interface.
 * 
 * Please edit '/common.properties' to switch the new class.
 */
public class SampleUserInfo
	extends DefaultUserInfo
{
	/** UID */
	private static final long serialVersionUID = -2024L;


	/**
     * Get item1.
     * @return item1
     */
    public String getItem1()
    {
    	return _item1;
    }

    /**
     * Set item1.
     * @param  pmData item1
     */
    public void setItem1(
        final String pmData
    )
    {
    	_item1 = pmData;
    }


    /** expand item */
    private String _item1;
}
