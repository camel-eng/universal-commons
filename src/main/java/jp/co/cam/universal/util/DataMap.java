package jp.co.cam.universal.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * [universal](commons)This class provides functionality for case-insensitive maps.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2005.09 Changed Hashtable to ConcurrentHashMap
 * <br>* End of support for JDK below 5
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public interface DataMap<K, V>
    extends Map<K, V>
{
	/**
     * This method returns the duplicate of the Map.
     * @return deep copy
     */
    DataMap<K, V> copy();


    /**
     * This class is the utility class exclusively for DataMap.
     */
    final class DataMapUtil
    {
        /**
         * This method converts the key to uppercase, if it's a string.
         * @param  pmKey Key
         * @return Key
         */
        public static Object convertionUpperKey(final Object pmKey)
        {
            if (pmKey instanceof String) {
                return ((String)pmKey).toUpperCase();
            } else {
                return pmKey;
            }
        }

        /**
         * This method returns the duplicate of the List.
         * @param  pmVal java.util.List
         * @return deep copy
         */
		public static List<Object> copy(
            final List<?> pmVal
        )
        {
            final List<Object> result;

            // ====================================================================================
            // Create outer frame
            // ====================================================================================
	        try {

	        	@SuppressWarnings("unchecked")
				final Class<List<Object>> type
				 = (Class<List<Object>>)pmVal.getClass();

		        result = type.getConstructor().newInstance();

	        } catch(final Exception wr) {
                throw new UnsupportedOperationException("clone failed - " + pmVal.getClass());
            }

            // ====================================================================================
            // Copy values
            // ====================================================================================
            for (final Object val : pmVal)
            {
                if (val instanceof List) {
                    result.add(copy((List<?>)val));
                } else
                if (val instanceof Map) {
                    result.add(copy((Map<?, ?>)val));
                } else {

                    try {
                        result.add(val.getClass().getMethod("clone").invoke(val));
                    } catch(final Exception wr) {
                        result.add(val);
                    }
                }
            }
            return result;
        }

        /**
         * This method returns the duplicate of the Map.
         * @param  pmVal java.util.Map
         * @return deep copy
         */
        public static Map<Object, Object> copy(
            final Map<?, ?> pmVal
        )
        {
            final Map<Object, Object> result;

            // ====================================================================================
            // Create outer frame
            // ====================================================================================
            try {

	        	@SuppressWarnings("unchecked")
				final Class<Map<Object, Object>> type
				 = (Class<Map<Object, Object>>)pmVal.getClass();

		        result = type.getConstructor().newInstance();

            } catch(final Exception wr) {
                throw new UnsupportedOperationException("clone failed - " + pmVal.getClass());
            }

            // ====================================================================================
            // Copy values
            // ====================================================================================
            Object val;

            for (final Object key : pmVal.keySet())
            {
                val = pmVal.get(key);

                if (val instanceof List) {
                    result.put(key, copy((List<?>)val));
                } else
                if (val instanceof Map) {
                    result.put(key, copy((Map<?, ?>)val));
                } else {

                    try {
                        result.put(key, val.getClass().getMethod("clone").invoke(val));
                    } catch(final Exception wr) {
                        result.put(key, val);
                    }
                }
            }
            return result;
        }
    }


    /**
     * This class is the standard class for DataMap.
     */
    @SuppressWarnings({ "unchecked"})
    final class UnSynchronizeDataMap<K, V>
        extends HashMap<K, V> implements DataMap<K, V>, Cloneable
    {
        /** UID */
        private static final long serialVersionUID = 2016L;


        @Override
        public boolean containsKey(
            final Object pmKey
        )
        {
            return super.containsKey(DataMapUtil.convertionUpperKey(pmKey));
        }

        @Override
        public V remove(
            final Object pmKey
        )
        {
            return super.remove(DataMapUtil.convertionUpperKey(pmKey));
        }

        @Override
        public V get(
            final Object pmKey
        )
        {
            return super.get(DataMapUtil.convertionUpperKey(pmKey));
        }

        @Override
        public V put(
            final K pmKey
          , final V pmVal
        )
        {
            return super.put((K)DataMapUtil.convertionUpperKey(pmKey), pmVal);
        }

        @Override
        public void putAll(
            final Map<? extends K, ? extends V> pmVal
        )
        {
            if (pmVal != null) {

                for (final K key : pmVal.keySet())
                {
                    put(key, pmVal.get(key));
                }
            }
        }


        @Override
        public DataMap<K, V> clone()
        {
            return (DataMap<K, V>)super.clone();
        }

        @Override
        public DataMap<K, V> copy()
        {
            return (DataMap<K, V>)DataMapUtil.copy(this);
        }


        /**
         * Constructor.
         * @param  pmData java.util.Map
         */
        public UnSynchronizeDataMap(
            final Map<K, V> pmData
        )
        {
            super();

            putAll(pmData);
        }

        /** Constructor. */
        public UnSynchronizeDataMap()
        {
            this(null);
        }
    }


    /**
     * This class is the synchronized DataMap class.
     */
    @SuppressWarnings({ "unchecked"})
    final class SynchronizeDataMap<K, V>
        extends ConcurrentHashMap<K, V> implements DataMap<K, V>, Cloneable
    {
        /** UID */
        private static final long serialVersionUID = 2016L;


        @Override
        public boolean containsKey(
            final Object pmKey
        )
        {
            return super.containsKey(DataMapUtil.convertionUpperKey(pmKey));
        }

        @Override
        public V remove(
            final Object pmKey
        )
        {
            return super.remove(DataMapUtil.convertionUpperKey(pmKey));
        }

        @Override
        public V get(
            final Object pmKey
        )
        {
            return super.get(DataMapUtil.convertionUpperKey(pmKey));
        }

		@Override
        public V put(
            final K pmKey
          , final V pmVal
        )
        {
			return super.put((K)DataMapUtil.convertionUpperKey(pmKey), pmVal);
        }

		@Override
        public void putAll(
            final Map<? extends K, ? extends V> pmVal
        )
        {
            if (pmVal != null) {

                for (final K key : pmVal.keySet())
                {
                    put(key, pmVal.get(key));
                }
            }
        }


		@Override
        public DataMap<K, V> clone()
            throws CloneNotSupportedException
        {
            return (DataMap<K, V>)super.clone();
        }

		@Override
        public DataMap<K, V> copy()
        {
            return (DataMap<K, V>)DataMapUtil.copy(this);
        }


        /**
         * Constructor.
         * @param  pmData java.util.Map
         */
        public SynchronizeDataMap(
            final Map<K, V> pmData
        )
        {
            super();

            putAll(pmData);
        }

        /** Constructor. */
        public SynchronizeDataMap()
        {
            this(null);
        }
    }
}
