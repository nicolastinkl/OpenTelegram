package org.xbill.DNS.config;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

/* loaded from: classes4.dex */
interface IPHlpAPI extends Library {
    public static final IPHlpAPI INSTANCE = (IPHlpAPI) Native.load("IPHlpAPI", IPHlpAPI.class, W32APIOptions.ASCII_OPTIONS);

    int GetAdaptersAddresses(int i, int i2, Pointer pointer, Pointer pointer2, IntByReference intByReference);

    @Structure.FieldOrder({"Length", "IfIndex", "Next", "AdapterName", "FirstUnicastAddress", "FirstAnycastAddress", "FirstMulticastAddress", "FirstDnsServerAddress", "DnsSuffix", "Description", "FriendlyName", "PhysicalAddress", "PhysicalAddressLength", "Flags", "Mtu", "IfType", "OperStatus", "Ipv6IfIndex", "ZoneIndices", "FirstPrefix", "TransmitLinkSpeed", "ReceiveLinkSpeed", "FirstWinsServerAddress", "FirstGatewayAddress", "Ipv4Metric", "Ipv6Metric", "Luid", "Dhcpv4Server", "CompartmentId", "NetworkGuid", "ConnectionType", "TunnelType", "Dhcpv6Server", "Dhcpv6ClientDuid", "Dhcpv6ClientDuidLength", "Dhcpv6Iaid", "FirstDnsSuffix"})
    public static class IP_ADAPTER_ADDRESSES_LH extends Structure {
        public WString DnsSuffix;
        public int OperStatus;

        public IP_ADAPTER_ADDRESSES_LH(Pointer pointer) {
            super(pointer);
            read();
        }
    }
}
