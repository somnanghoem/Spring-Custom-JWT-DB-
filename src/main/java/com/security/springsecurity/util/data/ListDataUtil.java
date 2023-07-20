package com.security.springsecurity.util.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ListDataUtil extends ArrayList<LinkedHashMap<String, Object>> {

	private static final long serialVersionUID = 8154940219462381299L;
	
	
	public ListDataUtil() {
		super();
	}
	
	public ListDataUtil( List map ) {
		super( map );
	}
	
	public Object[] getKeys() {
		Object[] keyArr = new Object[0];
		if ( size() > 0 ) {
			keyArr = get( 0 ).keySet().toArray();
		}
		return keyArr;
	}
	
	public void put( Object key , List l ) {
		for ( int i = 0; i < l.size(); i++ ) {
			if ( size() < i + 1 ) {
				add( new DataUtil() );
			}
			( (Map)get( i ) ).put( key, l.get( i ) );
		}
	}
	
	public void putAll( List m ) {
		clear();
		addAll( m );
	}
	
	public void removeColumn( String key ) {
		for(int i=0; i < this.size(); i++) {
			
			if(this.get(i).containsKey(key)) {
				this.get(i).remove(key);
			}
		}
	}
	
	public void removeRow( int keyIndex ) {
		remove( keyIndex );
	}
	
	public Object getKeyWithIndex( int keyIndex ) {
		return getKeyWithIndex( keyIndex, 0 );
	}
	
	public Object getKeyWithIndex( int keyIndex, int index ) {
		Object retObj = null;
		Set<String> tempSet = this.get( index ).keySet();

		if ( keyIndex >= tempSet.size() ) {

		}
		Iterator<String> iterator = tempSet.iterator();
		for ( int inx = 0; inx <= keyIndex; inx++ ) {
			retObj = iterator.next();
		}
		return retObj;
	}
	
	public List<DataUtil> get( Object key ) {
		ArrayList list = new ArrayList();  
		for ( int i = 0; i < size(); i++ ) {
			list.add( get( i ).get( key ) );
		}
		return list;
	}
	
	public List<DataUtil> toListDataUtil() {
		List<DataUtil> listDataUtil = new ArrayList<DataUtil>();
		for ( LinkedHashMap<String, Object> map : this ) {
			listDataUtil.add( new DataUtil( map ) );
		}
		return listDataUtil;
	}
	
	public void addDataUtil( DataUtil DataUtil ) {
		add( new DataUtil( DataUtil ) );
	}
	
	public void modify( String key, int index, Object replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modify(String key, int index, Object replaceValue)");
		}
		if ( !get( index ).containsKey( key ) ) {
			new Exception( "modify(String key, int index, Object replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	
	public void modifyBoolean( String key, int index, boolean replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyBoolean(String key, int index, boolean replaceValue)");
		}
		if ( !get( index ).containsKey( key ) ) {
			new Exception( "modifyBoolean(String key, int index, boolean replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyString( String key, int index, String replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyString(String key, int index, String replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyInt( String key, int index, int replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modify(Object key, int index, Object replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyDouble( String key, int index, double replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyDouble(String key, int index, double replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyFloat( String key, int index, float replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyFloat(String key, int index, float replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyLong( String key, int index, long replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modify(Object key, int index, Object replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyShort( String key, int index, short replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyShort(String key, int index, short replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void modifyBigDecimal( String key, int index, BigDecimal replaceValue ) {
		if ( !( size() > index ) ) {
			new Exception( "modifyBigDecimal(String key, int index, BigDecimal replaceValue)");
		}
		get( index ).put( key, replaceValue );
	}
	
	public void add( String key, Object value ) {
		boolean add = false;
		for ( int i = 0; i < size(); i++ ) {
			if ( !get( i ).containsKey( key ) ) {
				get( i ).put( key, value );
				add = true;
				break;
			}
		}
		if ( !add ) {
			DataUtil row = new DataUtil();
			//row.set( key, value );
			add( row );
		}
	}
	
	private Object getObject( Object key, int index ) {
		try {
			if ( size() <= index ) {
				return null;
			} else {
				return get( index ).get( key );
			}
		} catch ( IndexOutOfBoundsException ioe ) {
			new Exception( "getObject(Object key, int index)");
		}
		return null;
	}
	
	public Object get( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			return "";
		}
		return o;
	}
	
	public Object get( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return get( key, index );
	}
	
	public String getString( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			return null;
		} else {
			// If the key type is BigDecimal, it is represented as 0E-8 when the decimal point is set at least 8 digits.
			if ( o instanceof BigDecimal ) {
				return ( (BigDecimal) o ).toPlainString();
			} else {
				return o.toString();
			}
		}
	}
	
	public String getString( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getString( key, index );
	}
	
	public boolean getBoolean( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			return false;
		} else {
			if ( o instanceof Boolean ) {
				return ( (Boolean) o ).booleanValue();
			}
			if ( o instanceof String ) {
				try {
					return Boolean.valueOf( o.toString() ).booleanValue();
				} catch ( Exception e ) {
					new Exception( "getBoolean(Object key, int index)");
				}
			}
			new Exception( "getBoolean(Object key, int index)");
		}
		return false; // prevent compile error line. unreachable block.
	}
	
	public boolean getBoolean( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getBoolean( key, index );
	}
	
	public int getInt( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			return 0;
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).intValue();
			}
			if ( o instanceof String ) {
				try {
					return Integer.parseInt( o.toString() );
				} catch ( Exception e ) {
					new Exception( "getInt(Object key, int index)");
				}
			}
		}
		return 0; // prevent compile error line. It's unreachable block.
	}
	
	public int getInt( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getInt( key, index );
	}
	
	public double getDouble( Object key, int index ) {
		Object o = getObject( key, index );

		if ( o == null ) {
			return 0.0D;
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).doubleValue();
			}
			if ( o instanceof String ) {
				try {
					return Double.parseDouble( o.toString() );
				} catch ( Exception e ) {
					new Exception( "getDouble(Object key, int index)");
				}
			}
		}
		return 0.0D; // prevent compile error line. unreachable block.
	}
	
	public double getDouble( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getDouble( key, index );
	}
	
	public float getFloat( Object key, int index ) {
		Object o = getObject( key, index );

		if ( o == null ) {
			return 0.0F;
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).floatValue();
			}
			if ( o instanceof String ) {
				try {
					return Float.parseFloat( o.toString() );
				} catch ( Exception e ) {
					new Exception( "getFloat(Object key, int index)");
				}
			}
		}
		return 0.0F; // prevent compile error  line. unreachable block.
	}
	
	public float getFloat( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getFloat( key, index );
	}
	
	public long getLong( Object key, int index ) {
		Object o = getObject( key, index );

		if ( o == null ) {
			return 0L;
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).longValue();
			}
			if ( o instanceof String ) {
				try {
					return Long.parseLong( o.toString() );
				} catch ( Exception e ) {
					new Exception( "getLong(Object key, int index)");
				}
			}
		}
		return 0L; // prevent compile error line. unreachable block.
	}
	
	public long getLong( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getLong( key, index );
	}
	
	public short getShort( Object key, int index ) {
		Object o = getObject( key, index );

		if ( o == null ) {
			return 0;
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).shortValue();
			}
			
			if ( o instanceof String ) {
				try {
					return Short.parseShort( o.toString() );
				} catch ( Exception e ) {
					new Exception( "getShort(Object key, int index)");
				}
			}
		}
		return 0; // prevent compile error line. unreachable block.
	}
	
	public short getShort( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getShort( key, index );
	}
	
	public BigDecimal getBigDecimal( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			return new BigDecimal( 0 );
		} else {
			if ( o instanceof BigDecimal ) {
				return (BigDecimal) o;
			}
			
			// NOTE
			if ( o instanceof Number ) {
				return new BigDecimal( ( (Number) o ).doubleValue() );
			}
			
			if ( o instanceof String ) {
				try {
					return new BigDecimal( (String) o );
				} catch ( Exception e ) {
					new Exception( "getBigDecimal(Object key, int index)");
				}
			}
		}
		return new BigDecimal(0); // prevent compile error line. unreachable block.
	}
	
	public BigDecimal getBigDecimal( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getBigDecimal( key, index );
	}
	
	public int getDataCount() {
		return this.size();
	}
	
	public DataUtil getDataUtil( int key ) {
		Object obj = get( key );
		if ( obj instanceof DataUtil ) {
			return (DataUtil) obj;
		} else if ( obj instanceof LinkedHashMap ) {
			return new DataUtil( (LinkedHashMap<String, Object>) obj );
		} else {
			return obj == null ? new DataUtil() : (DataUtil) obj;
		}
	}
	
	public void addMMultiData( ListDataUtil DataUtil ) {
		int cnt = DataUtil.size();
		for ( int i = 0; i < cnt; i++ ) {
			this.add( new DataUtil( DataUtil.get( i ) ) );
		}
	}
	
	public void addMMultiDataNoClone( ListDataUtil DataUtil ) {
		int cnt = DataUtil.size();
		for ( int i = 0; i < cnt; i++ ) {
			this.add( DataUtil.get( i ) );
		}
	}
	
	public void addDataUtilNoClone(DataUtil data) {
		add (data);
	}
	
	public int getKeyCount() {
		int keyCount = 0;
		if(size() > 0 ) keyCount = get(0).keySet().size();
		return keyCount;
	}
	
	public int getDataCount(Object key) {
		return this.size();
	}
	
	public boolean containsKey(Object key) {
		if( this.size() == 0 ) {
			return false; 
		} else {
			return containsKey(0, key);
		}
	}
	
	public boolean containsKey(int index, Object key) {
		if( index >= this.size() ) {
			return false;
		} else {
			return get(index).containsKey(key);
		}
	}
	
	
}
