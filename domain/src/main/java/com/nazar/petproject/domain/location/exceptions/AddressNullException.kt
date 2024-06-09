package com.nazar.petproject.domain.location.exceptions

import com.nazar.petproject.domain.DomainException

class AddressNullException : DomainException("Address is null in fused location provider result")