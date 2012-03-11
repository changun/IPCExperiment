/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Cheng-Kang\\Desktop\\eclipse\\workspace\\IPCExperiement\\src\\edu\\ucla\\cens\\ipc\\IMemoryShare.aidl
 */
package edu.ucla.cens.ipc;
/**
 * Example of defining an interface for calling on to a remote service
 * (running in another process).
 */
public interface IMemoryShare extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.ucla.cens.ipc.IMemoryShare
{
private static final java.lang.String DESCRIPTOR = "edu.ucla.cens.ipc.IMemoryShare";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.ucla.cens.ipc.IMemoryShare interface,
 * generating a proxy if needed.
 */
public static edu.ucla.cens.ipc.IMemoryShare asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.ucla.cens.ipc.IMemoryShare))) {
return ((edu.ucla.cens.ipc.IMemoryShare)iin);
}
return new edu.ucla.cens.ipc.IMemoryShare.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_write:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.write(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_get_file_descriptor:
{
data.enforceInterface(DESCRIPTOR);
android.os.ParcelFileDescriptor _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
edu.ucla.cens.ipc.IProfileService _arg4;
_arg4 = edu.ucla.cens.ipc.IProfileService.Stub.asInterface(data.readStrongBinder());
this.get_file_descriptor(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.ucla.cens.ipc.IMemoryShare
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void write(int packet_id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(packet_id);
mRemote.transact(Stub.TRANSACTION_write, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void get_file_descriptor(android.os.ParcelFileDescriptor mf, int size, int test_id, int times, edu.ucla.cens.ipc.IProfileService mService) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mf!=null)) {
_data.writeInt(1);
mf.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(size);
_data.writeInt(test_id);
_data.writeInt(times);
_data.writeStrongBinder((((mService!=null))?(mService.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_get_file_descriptor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_write = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_get_file_descriptor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void write(int packet_id) throws android.os.RemoteException;
public void get_file_descriptor(android.os.ParcelFileDescriptor mf, int size, int test_id, int times, edu.ucla.cens.ipc.IProfileService mService) throws android.os.RemoteException;
}
