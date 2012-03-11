/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Cheng-Kang\\Desktop\\eclipse\\workspace\\IPCExperiement\\src\\edu\\ucla\\cens\\ipc\\IProfileService.aidl
 */
package edu.ucla.cens.ipc;
/**
 * Example of defining an interface for calling on to a remote service
 * (running in another process).
 */
public interface IProfileService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.ucla.cens.ipc.IProfileService
{
private static final java.lang.String DESCRIPTOR = "edu.ucla.cens.ipc.IProfileService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.ucla.cens.ipc.IProfileService interface,
 * generating a proxy if needed.
 */
public static edu.ucla.cens.ipc.IProfileService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.ucla.cens.ipc.IProfileService))) {
return ((edu.ucla.cens.ipc.IProfileService)iin);
}
return new edu.ucla.cens.ipc.IProfileService.Stub.Proxy(obj);
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
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.init(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_profile_one:
{
data.enforceInterface(DESCRIPTOR);
this.profile_one();
reply.writeNoException();
return true;
}
case TRANSACTION_reply:
{
data.enforceInterface(DESCRIPTOR);
this.reply();
reply.writeNoException();
return true;
}
case TRANSACTION_end:
{
data.enforceInterface(DESCRIPTOR);
this.end();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.ucla.cens.ipc.IProfileService
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
public void init(int test_id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(test_id);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void profile_one() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_profile_one, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void reply() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_reply, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void end() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_end, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_profile_one = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_reply = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_end = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void init(int test_id) throws android.os.RemoteException;
public void profile_one() throws android.os.RemoteException;
public void reply() throws android.os.RemoteException;
public void end() throws android.os.RemoteException;
}
