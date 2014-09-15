package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.vdscommands.GetVmsFromExternalProviderParameters;

public class GetVmsFromExternalProviderVDSCommand<T extends GetVmsFromExternalProviderParameters> extends VdsBrokerCommand<T> {
    private VMListReturnForXmlRpc vmListReturn;

    public GetVmsFromExternalProviderVDSCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected void executeVdsBrokerCommand() {
        vmListReturn = getBroker().getExternalVmList(getParameters().getUrl(),
                getParameters().getUsername(), getParameters().getPassword());
        proceedProxyReturnValue();
        List<VM> vms = new ArrayList<>();
        for (Map<String, Object> map : vmListReturn.mVmList) {
            VM vm = VdsBrokerObjectsBuilder.buildVmsDataFromExternalProvider(map);
            if (vm != null) {
                vms.add(vm);
            }
        }
        setReturnValue(vms);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return vmListReturn.mStatus;
    }

    @Override
    protected Object getReturnValueFromBroker() {
        return vmListReturn;
    }

    @Override
    protected boolean getIsPrintReturnValue() {
        return false;
    }
}
