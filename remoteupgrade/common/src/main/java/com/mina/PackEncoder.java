package com.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;

public class PackEncoder implements ProtocolEncoder {
	private final Charset charset;

	public PackEncoder(Charset charset) {
		// TODO Auto-generated constructor stub
		this.charset=charset;
	}
	
	
	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void encode(IoSession arg0, Object message,
			ProtocolEncoderOutput output) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buf = IoBuffer.allocate(1024).setAutoExpand(true);
		 
		buf.put(charset.encode(message.toString()));

		buf.flip();
		
		output.write(buf);

	}

}
