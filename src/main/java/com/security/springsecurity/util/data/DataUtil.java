package com.security.springsecurity.util.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataUtil extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = -5861114305569703387L;

	public DataUtil() {
		super();
	}
	
	public DataUtil( Map<String, Object> param ) {
		super( param );
	}
	
	public String getString( String key ) {
		if ( get( key ) != null ) {
			return String.valueOf( get( key ) );
		}
		return null;
	}
	
	public BigDecimal getBigDecimal( String key ) {
		if ( get( key ) != null && !getString( key ).isEmpty() ) {
			return new BigDecimal( getString( key ) );
		}
		return BigDecimal.ZERO;
	}
	
	public Long getLong( String key ) {
		if ( get( key ) != null ) {
			return Long.valueOf( getString( key ) ).longValue();
		}
		return 0L;
	}
	
	public int getInt( String key ) {
		if ( get( key ) != null ) {
			return Integer.valueOf( getString( key ) ).intValue();
		}
		return 0;
	}
	
	public short getShort( String key ) {
		if ( get( key ) != null ) {
			return Short.valueOf( getString( key ) ).shortValue();
		}
		return 0;
	}
	
	public double getDouble( String key ) {
		if ( get( key ) != null ) {
			return Double.valueOf( getString( key ) ).doubleValue();
		}
		return 0.0D;
	}
	
	public float getFloat( String key ) {
		if ( get( key ) != null ) {
			return Float.valueOf( getString( key ) ).floatValue();
		}
		return 0.0F;
	}
	
	@SuppressWarnings("unchecked")
	public DataUtil getDataUtil( String key ) {
		try {
			Object obj = get( key );
			if ( obj instanceof DataUtil ) {
				return (DataUtil) obj;
			} else if ( obj instanceof LinkedHashMap ) {
				return new DataUtil( (LinkedHashMap<String, Object>) obj );
			}
			
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return new DataUtil();
	}
	
	public void set( String key, Object value ) {
		this.put( key, value );
	}
	
	public void setString( String key, String value ) {
		this.put( key, value );
	}
	
	public void setBigDecimal( String key, BigDecimal value ) {
		this.put( key, value );
	}
	
	public void setLong( String key, long value ) {
		this.put( key, value );
	}
	
	public void setInt( String key, int value ) {
		this.put( key, value );
	}

	public void setBoolean( String key, boolean value ) {
		this.put( key, value );
	}
	
	public void setShort( String key, short value ) {
		this.put( key, value );
	}

	public void setDouble( String key, double value ) {
		this.put( key, value );
	}

	public void setFloat( String key, float value ) {
		this.put( key, value );
	}
	
	public void setDataUtil( String key, DataUtil value ) {
		this.put( key, value );
	}
		
	public void appendFrom( DataUtil data ) {
		this.putAll( data );
	}
	
	public void setListDataUtil( String key, ListDataUtil value ) {
		this.put( key, value );
	}
	
	@SuppressWarnings( { "unchecked" } )
	public ListDataUtil getListDataUtil( String key ) {
		try {
			Object obj = get( key );
			if ( obj instanceof ListDataUtil ) {
				return (ListDataUtil) obj;
			} else if ( obj instanceof ArrayList ) {
				return  new ListDataUtil( (List<LinkedHashMap<String, Object>>) obj );
			} else {
				return obj == null ? new ListDataUtil() : (ListDataUtil) obj;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return new ListDataUtil();
	}
}
