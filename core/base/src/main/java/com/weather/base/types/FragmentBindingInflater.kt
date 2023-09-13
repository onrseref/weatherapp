package com.weather.base.types

import android.view.LayoutInflater
import android.view.ViewGroup

typealias FragmentBindingInflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
