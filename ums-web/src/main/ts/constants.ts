module ums {
  export class Constants {
    static  Default():any {
      return {
        gender: {
          'M': 'Male',
          'F': 'Female'
        },
        programType: {
          '11': 'Undergraduate',
          '22': 'Postgraduate'
        },
        deptShort: {
          '01': 'ARC',
          '02': 'BBA',
          '03': 'CE',
          '04': 'CSE',
          '05': 'EEE',
          '06': 'TE',
          '07': 'MPE'
        },
        deptLong: {
          '01': 'Department of Architecture',
          '02': 'School of Business',
          '03': 'Department of Civil Engineering',
          '04': 'Department of Computer Science and Engineering',
          '05': 'Department of Electrical and Electronic Engineering',
          '06': 'Department of Textile Engineering',
          '07': 'Department of Mechanical & Production Engineering'
        },
        officeShort: {
          '80': 'RO',
          '81': 'CO',
          '82': 'TO',
          '83': 'EO',
          '84': 'AoSW',
          '85': 'PO'
        },
        officeLong: {
          '80': 'Office of the Registrar',
          '81': 'Office of the Controller of Examinations',
          '82': 'Office of the Treasurer',
          '83': 'Engineering Office',
          '84': "Office of the Advisor of Student''s Welfare",
          '85': 'Proctor Office'
        },
        courseType: {
          '1': 'Theory',
          '2': 'Sessional',
          '3': 'Thesis/Project'
        },
        courseCategory: {
          '1': 'Mandatory',
          '2': 'Optional'
        },
        status: {
          '0': 'Inactive',
          '1': 'Active'
        }
      };
    }
  }
}