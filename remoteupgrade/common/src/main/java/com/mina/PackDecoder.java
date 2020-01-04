package com.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

public class PackDecoder extends CumulativeProtocolDecoder {
	private final Charset charset;

	public PackDecoder(Charset charset) {
		this.charset = charset;

	}

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub

		String s = charset.decode(arg1.buf()).toString();
		if (s.isEmpty()) {
			return false;
		}

		arg2.write(s);
		return true;
	}

}
