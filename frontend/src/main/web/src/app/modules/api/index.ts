import { HttpHeaders } from '@angular/common/http';

let baseUrl;
const apiPort = ':80/'
//const apiPort = '/'

// baseUrl = `http://startreker-netcracker.herokuapp.com${apiPort}`
baseUrl = `http://localhost${apiPort}`

export const HttpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
}

export const HttpOptionsAuthorized = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': `Bearer ${localStorage.getItem('at')}`,
    'Authorization-Refresh': `Bearer ${localStorage.getItem('rt')}`
  }),
  observe: 'response' as 'response'
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
  },
  confirmPassword(){
    return `${baseUrl}api/auth/confirm-password`;
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
    return `${baseUrl}api/v1/planets-spaceports`;
  },
  addTrip() {
    return `${baseUrl}api/v1/trips`;
  },
  updateTrip(id) {
    return `${baseUrl}api/v1/trips/${id}`
  },
  getAllTrips() {
    return `${baseUrl}api/v1/trips`;
  },
  getCarrierTrips() {
    return `${baseUrl}api/v1/carrier/trips`;
  },
  addTicketClass() {
    return `${baseUrl}api/v1/ticket-class`;
  },
  deleteTicketClass(id) {
    return `${baseUrl}api/v1/ticket-class/${id}`;
  },
  deleteTrip(id) {
    return `${baseUrl}api/v1/trip/${id}`;
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

const trip = {
  trips(){
    return `${baseUrl}api/v1/approver/trip`;
  },
  update(){
    return `${baseUrl}api/v1/trip`;
  }
}

const landing = {
  planets(){
    return `${baseUrl}api/v1/planets`;
  },
  spaceports(){
    return `${baseUrl}api/v1/spaceports`;
  },
  trips(){
    return `${baseUrl}api/v1/user/trips`;
  }
}

export const Api = {
  HttpOptions,
  HttpOptionsAuthorized,
  auth,
  dashboard,
  carrier,
  costDash,
  baseUrl,
  service, 
  possibleServices,
  trip,
  trips,
  landing
}

export const options = {
  root: baseUrl
}

export function checkToken(heads: HttpHeaders){
  if (heads.has('New-Access-Token')) {
    localStorage.removeItem('at');
    localStorage.setItem('at', heads.get('New-Access-Token'));
  }
}
