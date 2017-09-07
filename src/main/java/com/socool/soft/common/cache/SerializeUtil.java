package com.socool.soft.common.cache;

import java.io.*;

public final class SerializeUtil {
	protected static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

    protected static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
		} finally {
			try {
				bais.close();
				ois.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
}
