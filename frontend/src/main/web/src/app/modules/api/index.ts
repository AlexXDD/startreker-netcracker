import { HttpHeaders } from '@angular/common/http';

let baseUrl;
const apiPort = ':80/'
// const apiPort = '/'

// baseUrl = `http://startreker-netcracker.herokuapp.com${apiPort}`
baseUrl = `http://localhost${apiPort}`

export const HttpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    // 'Access-Control-Allow-Origin': '*'
  })
}

export const HttpOptionsAuthorized = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    // 'Access-Control-Allow-Origin': '*',
    // 'Authorization': `Bearer ${localStorage.getItem('at')}`,
    // 'Authorization-Refresh': `Bearer ${localStorage.getItem('rt')}`
  })
}

const auth = {
  loginUser() {
    return `${baseUrl}api/auth/sign-in`;
  },
  registerUser() {
    return `${baseUrl}api/auth/sign-up`;
  },
  logoutUser() {
    return `${baseUrl}api/log-out`;
  },
  recoverPassword() {
    return `${baseUrl}api/auth/password-recovery`;
  }
}

const dashboard = {
  tripDistribution() {
    return `${baseUrl}api/v1/trip/distribution`;
  },
  serviceDistribution() {
    return `${baseUrl}api/v1/service/distribution`;
  }
}

const carrier = {
  carriers(){
    return `${baseUrl}api/v1/admin/carrier`;
  },
  getCarriersPagin(){
    return `${baseUrl}api/v1/admin/pagination`;
  },
  getCarrierByUsername(){
    return `${baseUrl}api/v1/admin/carrier-by-username?username=`;
  }
}

const bundles = {
  bundles(){
    return `${baseUrl}api/v1/admin/carrier`;
  },
  getBundlesPagin(){
    return `${baseUrl}api/v1/admin/pagination`;
  }
/*  getCarrierByUsername(){
    return `${baseUrl}api/v1/admin/carrier-by-username?username=`;
  } */
}
const trips = {
  getExistingPlanets() {
    return `${baseUrl}api/v1/planets`;
  },
  addTrip() {
    return `${baseUrl}api/v1/trips`;
  },
  getAllTrips() {
    return `${baseUrl}api/v1/trips`;
  },
  getCarrierTrips() {
    return `${baseUrl}api/v1/carrier/trips`;
  }
 }

const costDash = {
  getCosts(){
    return `${baseUrl}api/v1/admin/costs`;
  }
}

const possibleServices = {
  possibleServices(){
    return `${baseUrl}api/v1/possible-services`;
  }
}

const service = {
  services(){
    return `${baseUrl}api/v1/carrier/service`;
  },
  paginServices(){
    return `${baseUrl}api/v1/carrier/service/pagin`;
  },
  servicesByStatus(){
    return `${baseUrl}api/v1/carrier/service/by-status`;
  }
}

export const Api = {
  auth,
  dashboard,
  carrier,
  costDash,
  baseUrl,
  service, 
  possibleServices,
  trips
}

export const options = {
  root: baseUrl
}
